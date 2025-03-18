package com.example.automotiveapp.service.comment;

import com.example.automotiveapp.dto.CommentDto;

import java.util.List;

public interface FindCommentsByForumIdService {

    List<CommentDto> findCommentsByForumId(Long forumId);
}
