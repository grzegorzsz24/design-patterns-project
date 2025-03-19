package com.example.automotiveapp.service.like;

import com.example.automotiveapp.dto.LikeDto;

import java.util.List;

// L3 Mediator - third impl
public interface LikeMediator {
    LikeDto processLike(LikeDto likeDto);
    List<LikeDto> getPostLikes(Long postId);
    void deleteLike(Long likeId);
}
