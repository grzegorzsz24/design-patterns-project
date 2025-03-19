package com.example.automotiveapp.service.post;

import com.example.automotiveapp.domain.File;
import com.example.automotiveapp.domain.Post;
import com.example.automotiveapp.domain.User.User;
import com.example.automotiveapp.dto.PostDto;
import com.example.automotiveapp.dto.ReportDto;
import com.example.automotiveapp.exception.BadRequestException;
import com.example.automotiveapp.exception.ResourceNotFoundException;
import com.example.automotiveapp.mapper.PostDtoMapper;
import com.example.automotiveapp.reponse.PostResponse;
import com.example.automotiveapp.repository.*;
import com.example.automotiveapp.request.PostSaveRequest;
import com.example.automotiveapp.service.ContentFeed;
import com.example.automotiveapp.service.media.MediaAlbum;
import com.example.automotiveapp.service.media.MediaComponent;
import com.example.automotiveapp.service.media.MediaFileAdapter;
import com.example.automotiveapp.service.report.ReportMediator;
import com.example.automotiveapp.service.utils.SecurityUtils;
import com.example.automotiveapp.storage.FileStorageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

// L5 Interface Segregation - third usage
@Service
@RequiredArgsConstructor
@Slf4j
public class PostService implements PostSearchService, PostPersistenceService {
    private final PostRepository postRepository;
    private final PostDtoMapper postDtoMapper;
    private final FileStorageService fileStorageService;
    private final FileRepository fileRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final ReportMediator reportMediator;

    @Transactional
    public PostDto savePost(PostSaveRequest postToSave) {
        Post post = new Post();
        post.setContent(postToSave.getContent());
        post.setUser(userRepository.findByEmail(SecurityUtils.getCurrentUserEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Nie znaleziono użytkownika")));
        post.setPostedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        post.setLiked(false);
        post.setLikesNumber(0);
        post.setCommentsNumber(0);

        // L2 Composite - first usage
        if (postToSave.getFiles() != null) {
            MediaAlbum album = new MediaAlbum();
            List<String> savedImageNames = fileStorageService.saveImage(postToSave.getFiles());
            for (String imageName : savedImageNames) {
                File file = new File();
                file.setFileUrl(imageName);
                file.setPost(post);

                post.getFiles().add(file);

                MediaComponent mediaFile = new MediaFileAdapter(file);
                album.add(mediaFile);
            }

        }
        Post savedPost = postRepository.save(post);
        fileRepository.saveAll(post.getFiles());
        return PostDtoMapper.map(savedPost);
    }

    public void deletePost(Long id) {
        if (postRepository.findById(id).isEmpty()) {
            throw new ResourceNotFoundException("Nie znaleziono posta");
        }
        PostDto postDto = PostDtoMapper.map(postRepository.findById(id).get());
        for (String imageUrl : postDto.getImageUrls()) {
            StringBuilder modifiedImageUrl = new StringBuilder(imageUrl);
            modifiedImageUrl.delete(0, "http://localhost:8080/images/".length());
            fileStorageService.deleteFile(modifiedImageUrl.toString());
        }

        postRepository.deleteById(id);
    }


    public PostDto findPostById(long id) {
        PostDto postDto = PostDtoMapper.map(postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nie znaleziono postu")));
        postDto.setLiked(likeRepository.getLikeByUser_EmailAndPostId(SecurityUtils.getCurrentUserEmail(), id).isPresent());
        return postDto;
    }

    public void updatePost(PostDto postToUpdate) {
        Post post = postDtoMapper.map(postToUpdate);
        postRepository.save(post);
    }

    // L2 Composite - second usage
    public PostResponse getFriendsPosts(Pageable pageable) {
        final ContentFeed compositeFeed = new ContentFeed();

        List<User> friends = userRepository.findUserFriends(SecurityUtils.getCurrentUserEmail());
        for (User friend : friends) {
            List<Post> friendPosts = postRepository.findByUserOrderByPostedAtDesc(friend);
            for (Post p : friendPosts) {
                compositeFeed.add(new PostContent(p));
            }
        }

        List<User> publicProfiles = userRepository.findPublicProfiles();
        for (User publicProfile : publicProfiles) {
            if (!friends.contains(publicProfile)) {
                List<Post> publicProfilePosts = postRepository.findByUserOrderByPostedAtDesc(publicProfile);
                for (Post p : publicProfilePosts) {
                    compositeFeed.add(new PostContent(p));
                }
            }
        }
        PostDtoCollectorVisitor visitor = new PostDtoCollectorVisitor(likeRepository);

        compositeFeed.accept(visitor);

        List<PostDto> postDtos = visitor.getCollectedPosts();

        postDtos.sort(Comparator.comparing(PostDto::getPostedAt).reversed());

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), postDtos.size());
        List<PostDto> paginatedPosts = postDtos.subList(start, end);
        long totalPosts = postDtos.size();

        return new PostResponse(paginatedPosts, totalPosts);
    }

    public PostResponse getUserPosts(Long userId, Pageable pageable) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Nie znaleziono użytkownika"));

        if (user.isPublicProfile()) {
            List<PostDto> friendsPosts = new ArrayList<>();
            List<Post> posts = postRepository.findAllByUserIdOrderByPostedAtDesc(userId);
            setPostLikes(posts, friendsPosts);
            Page<PostDto> paginatedPosts = getPostDtos(pageable, friendsPosts);
            long totalPosts = friendsPosts.size();

            return new PostResponse(paginatedPosts.getContent(), totalPosts);
        } else {
            throw new BadRequestException("Użytkownik ma prywatny profil");
        }
    }

    private Page<PostDto> getPostDtos(Pageable pageable, List<PostDto> friendsPosts) {
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), friendsPosts.size());
        return new PageImpl<>(friendsPosts.subList(start, end), pageable, friendsPosts.size());
    }

    private void setPostLikes(List<Post> posts, List<PostDto> friendsPosts) {
        for (Post post : posts) {
            PostDto postDto = PostDtoMapper.map(post);
            postDto.setLiked(likeRepository.getLikeByUser_EmailAndPostId(SecurityUtils.getCurrentUserEmail(), post.getId()).isPresent());
            friendsPosts.add(postDto);
        }
    }

    public ReportDto reportPost(ReportDto reportDto) {
        return reportMediator.reportPost(reportDto);
    }
}
