package com.example.automotiveapp.service.post;

import com.example.automotiveapp.dto.PostDto;

public interface FindPostByIdService {

    PostDto findPostById(long id);
}
