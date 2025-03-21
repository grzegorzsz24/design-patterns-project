package com.example.automotiveapp.service.like;

import com.example.automotiveapp.domain.Like;
import com.example.automotiveapp.domain.article.Article;
import com.example.automotiveapp.dto.ArticleDto;
import com.example.automotiveapp.dto.LikeDto;
import com.example.automotiveapp.dto.PostDto;
import com.example.automotiveapp.exception.BadRequestException;
import com.example.automotiveapp.exception.ResourceNotFoundException;
import com.example.automotiveapp.mapper.ArticleDtoMapper;
import com.example.automotiveapp.mapper.LikeDtoMapper;
import com.example.automotiveapp.mapper.UserDtoMapper;
import com.example.automotiveapp.repository.ArticleRepository;
import com.example.automotiveapp.repository.LikeRepository;
import com.example.automotiveapp.repository.PostRepository;
import com.example.automotiveapp.service.UserService;
import com.example.automotiveapp.service.article.ArticleService;
import com.example.automotiveapp.service.post.PostService;
import com.example.automotiveapp.service.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeMediatorImpl implements LikeMediator {

    private final LikeRepository likeRepository;
    private final PostService postService;
    private final ArticleService articleService;
    private final PostRepository postRepository;
    private final ArticleRepository articleRepository;
    private final ArticleDtoMapper articleDtoMapper;
    private final UserService userService;
    private final UserDtoMapper userDtoMapper;
    private final LikeDtoMapper likeDtoMapper;

    // L4 Open - Closed - fourth impl
    @Override
    public LikeDto processLike(LikeDto likeDto) {
        if (likeDto.getPost() == null && likeDto.getArticle() == null) {
            throw new BadRequestException("Podaj post lub artykuł");
        }

        Like like = likeDtoMapper.map(likeDto);

        if (like.getPost() != null) {
            updatePostLike(like);
        } else if (like.getArticle() != null) {
            updateArticleLike(like);
        }

        return LikeDtoMapper.map(like);
    }

    @Override
    public List<LikeDto> getPostLikes(Long postId) {
        postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Nie znaleziono posta"));

        return likeRepository.findAllByPost_Id(postId).stream()
                .map(LikeDtoMapper::map)
                .toList();
    }

    @Override
    public void deleteLike(Long likeId) {
        likeRepository.deleteById(likeId);
    }

    private void updatePostLike(Like like) {
        PostDto likedPost = postService.findPostById(like.getPost().getId());
        Optional<Like> userLike = likeRepository
                .getLikeByUser_EmailAndPostId(SecurityUtils.getCurrentUserEmail(), like.getPost().getId());

        if (userLike.isPresent()) {
            likedPost.setLikesNumber(likedPost.getLikesNumber() - 1);
            likeRepository.delete(userLike.get());
        } else {
            likedPost.setLikesNumber(likedPost.getLikesNumber() + 1);
            likeRepository.save(like);
        }
        postService.updatePost(likedPost);
    }

    private void updateArticleLike(Like like) {
        ArticleDto likedArticle = articleService.findArticleById(like.getArticle().getId());

        Optional<Like> userLike = likeRepository
                .getLikeByUser_EmailAndArticleId(SecurityUtils.getCurrentUserEmail(), like.getArticle().getId());
        if (userLike.isPresent()) {
            likedArticle.setLikesNumber(likedArticle.getLikesNumber() - 1);
            likeRepository.delete(userLike.get());
        } else {
            likedArticle.setLikesNumber(likedArticle.getLikesNumber() + 1);
            likeRepository.save(like);
        }
        Article article = articleDtoMapper.map(likedArticle);
        article.setUser(userDtoMapper.map(
                userService.findUserByNickname(likedArticle.getUser()).orElseThrow(
                        () -> new ResourceNotFoundException("Nie znaleziono użytkownika"))
        ));
        articleRepository.save(article);
    }
}
