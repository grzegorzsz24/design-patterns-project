package com.example.automotiveapp.request.adapter;

import com.example.automotiveapp.dto.LikeDto;
import com.example.automotiveapp.request.LikeRequest;
import lombok.AllArgsConstructor;

// L2 Adapter - first impl
@AllArgsConstructor
public class LikeRequestAdapter extends LikeDto {
    private final LikeRequest likeRequest;


    @Override
    public Long getId() {
        return likeRequest.getId();
    }

    @Override
    public String getUser() {
        return likeRequest.getUser();
    }

    @Override
    public Long getPost() {
        return likeRequest.getPost();
    }

    @Override
    public Long getArticle() {
        return likeRequest.getArticle();
    }
}
