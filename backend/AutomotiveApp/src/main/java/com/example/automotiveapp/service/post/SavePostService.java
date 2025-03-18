package com.example.automotiveapp.service.post;

import com.example.automotiveapp.dto.PostDto;
import com.example.automotiveapp.request.PostSaveRequest;

public interface SavePostService {

    PostDto savePost(PostSaveRequest postToSave);
}
