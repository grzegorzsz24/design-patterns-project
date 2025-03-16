package com.example.automotiveapp.service.comment;

import com.example.automotiveapp.dto.CommentDto;

import java.util.List;
import java.util.Optional;

// L5 Dependency Inversion - sixth impl
public abstract class CommentService {

    public abstract CommentDto saveComment(CommentDto commentDto);

    public abstract Optional<CommentDto> findCommentById(long id);

    public abstract void updateComment(CommentDto commentToUpdate) ;

    public abstract void deleteComment(Long id);

    public abstract List<CommentDto> findCommentsByPostId(Long postId);

    public abstract List<CommentDto> findCommentsByForumId(Long forumId);

}
