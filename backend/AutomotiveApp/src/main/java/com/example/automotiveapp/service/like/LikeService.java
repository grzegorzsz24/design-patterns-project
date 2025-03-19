package com.example.automotiveapp.service.like;

import com.example.automotiveapp.dto.LikeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

// L2 Facade - third implementation
// L3 Mediator - third usage
@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeMediator likeMediator;

    public LikeDto saveLike(LikeDto likeDto) {
        return likeMediator.processLike(likeDto);
    }

    public List<LikeDto> getPostLikes(Long postId) {
        return likeMediator.getPostLikes(postId);
    }

    public void deleteLike(Long likeId) {
        likeMediator.deleteLike(likeId);
    }
}
