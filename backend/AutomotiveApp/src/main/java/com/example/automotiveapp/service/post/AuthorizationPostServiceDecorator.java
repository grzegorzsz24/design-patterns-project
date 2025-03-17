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
public class AuthorizationPostServiceDecorator implements PostSearchService, PostPersistenceService {

    private final PostSearchService searchDelegate;
    private final PostPersistenceService persistenceDelegate;

    public AuthorizationPostServiceDecorator(PostSearchService searchDelegate, PostPersistenceService persistenceDelegate) {
        this.searchDelegate = searchDelegate;
        this.persistenceDelegate = persistenceDelegate;
    }

    @Override
    public void deletePost(Long id) {
        PostDto post = searchDelegate.findPostById(id);
        System.out.println(SecurityUtils.getCurrentUserEmail() + post.getUser());
        if (!Objects.equals(SecurityUtils.getCurrentUserEmail(), post.getUser())) {
            throw new UnauthorizedAccessException("No delete permission : " + id);
        }
        persistenceDelegate.deletePost(id);
    }

    @Override
    public void updatePost(PostDto postToUpdate) {
        PostDto post = searchDelegate.findPostById(postToUpdate.getId());
        if (!Objects.equals(SecurityUtils.getCurrentUserEmail(), post.getUser())) {
            throw new UnauthorizedAccessException("No delete permission : " + postToUpdate.getId());
        }
        persistenceDelegate.updatePost(postToUpdate);
    }

    @Override
    public PostDto findPostById(long id) {
        return searchDelegate.findPostById(id);
    }

    @Override
    public PostDto savePost(PostSaveRequest postToSave) {
        return persistenceDelegate.savePost(postToSave);
    }

    @Override
    public PostResponse getUserPosts(Long userId, Pageable pageable) {
        return searchDelegate.getUserPosts(userId, pageable);
    }

    @Override
    public ReportDto reportPost(ReportDto reportDto) {
        return persistenceDelegate.reportPost(reportDto);
    }

    @Override
    public PostResponse getFriendsPosts(Pageable pageable) {
        return searchDelegate.getFriendsPosts(pageable);
    }
}
