package com.example.automotiveapp.service.post;

import com.example.automotiveapp.dto.PostDto;
import com.example.automotiveapp.dto.ReportDto;
import com.example.automotiveapp.exception.UnauthorizedAccessException;
import com.example.automotiveapp.reponse.PostResponse;
import com.example.automotiveapp.request.PostSaveRequest;
import com.example.automotiveapp.service.utils.SecurityUtils;
import org.springframework.data.domain.Pageable;

import java.util.Objects;

// L2 Decorator - first implementation
public class AuthorizationPostServiceDecorator implements PostServiceInterface {

    private final PostServiceInterface delegate;

    public AuthorizationPostServiceDecorator(PostServiceInterface delegate) {
        this.delegate = delegate;
    }

    @Override
    public void deletePost(Long id) {
        PostDto post = delegate.findPostById(id);
        System.out.println(SecurityUtils.getCurrentUserEmail() + post.getUser());
        if (!Objects.equals(SecurityUtils.getCurrentUserEmail(), post.getUser())) {
            throw new UnauthorizedAccessException("No delete permission : " + id);
        }
        delegate.deletePost(id);
    }

    @Override
    public void updatePost(PostDto postToUpdate) {
        PostDto post = delegate.findPostById(postToUpdate.getId());
        if (!Objects.equals(SecurityUtils.getCurrentUserEmail(), post.getUser())) {
            throw new UnauthorizedAccessException("No delete permission : " + postToUpdate.getId());
        }
        delegate.updatePost(postToUpdate);
    }

    @Override
    public PostDto findPostById(long id) {
        return delegate.findPostById(id);
    }

    @Override
    public PostDto savePost(PostSaveRequest postToSave) {
        return delegate.savePost(postToSave);
    }

    @Override
    public PostResponse getUserPosts(Long userId, Pageable pageable) {
        return delegate.getUserPosts(userId, pageable);
    }

    @Override
    public ReportDto reportPost(ReportDto reportDto) {
        return delegate.reportPost(reportDto);
    }

    @Override
    public PostResponse getFriendsPosts(Pageable pageable) {
        return delegate.getFriendsPosts(pageable);
    }
}
