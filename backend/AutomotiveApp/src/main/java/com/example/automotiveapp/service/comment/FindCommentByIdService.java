package com.example.automotiveapp.service.comment;

import com.example.automotiveapp.dto.CommentDto;

import java.util.Optional;

public interface FindCommentByIdService {

    Optional<CommentDto> findCommentById(long id);
}
