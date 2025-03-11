package com.example.automotiveapp.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LikeRequest {
    private Long id;
    private String user;
    private Long post;
    private Long article;
}
