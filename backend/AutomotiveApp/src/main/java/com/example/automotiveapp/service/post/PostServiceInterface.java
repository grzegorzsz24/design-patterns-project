package com.example.automotiveapp.service.post;

import com.example.automotiveapp.dto.PostDto;
import com.example.automotiveapp.dto.ReportDto;
import com.example.automotiveapp.reponse.PostResponse;
import com.example.automotiveapp.request.PostSaveRequest;
import org.springframework.data.domain.Pageable;

public interface PostServiceInterface {

    void deletePost(Long id);

    void updatePost(PostDto postToUpdate);

    PostDto findPostById(long id);

    PostDto savePost(PostSaveRequest postToSave);

    PostResponse getUserPosts(Long userId, Pageable pageable);

    ReportDto reportPost(ReportDto reportDto);

    PostResponse getFriendsPosts(Pageable pageable);
}