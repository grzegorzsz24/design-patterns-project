package com.example.automotiveapp.service.post;

import com.example.automotiveapp.domain.Post;
import com.example.automotiveapp.dto.PostDto;
import com.example.automotiveapp.mapper.PostDtoMapper;
import com.example.automotiveapp.repository.LikeRepository;
import com.example.automotiveapp.service.ContentFeed;
import com.example.automotiveapp.service.ContentVisitor;
import com.example.automotiveapp.service.forum.ForumContent;
import com.example.automotiveapp.service.utils.SecurityUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Getter
public class PostDtoCollectorVisitor implements ContentVisitor {
    private final LikeRepository likeRepository;
    private final List<PostDto> collectedPosts = new ArrayList<>();

    @Override
    public void visit(ContentFeed feed) {

    }

    @Override
    public void visit(ForumContent forum) {

    }

    @Override
    public void visit(PostContent postContent) {
        Post post = postContent.getPost();
        PostDto postDto = PostDtoMapper.map(post);

        boolean isLiked = likeRepository
                .getLikeByUser_EmailAndPostId(SecurityUtils.getCurrentUserEmail(), post.getId())
                .isPresent();

        postDto.setLiked(isLiked);
        collectedPosts.add(postDto);
    }
}
