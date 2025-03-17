package com.example.automotiveapp.service.comment;

// L5 Interface Segregation - second impl
public interface CommentPersistenceService extends DeleteCommentService, SaveCommentService, UpdateCommentService {
}
