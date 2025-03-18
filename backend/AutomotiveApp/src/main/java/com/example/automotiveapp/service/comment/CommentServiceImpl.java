package com.example.automotiveapp.service.comment;

import com.example.automotiveapp.domain.Comment;
import com.example.automotiveapp.domain.comment.CommentCollection;
import com.example.automotiveapp.domain.forum.Forum;
import com.example.automotiveapp.domain.Post;
import com.example.automotiveapp.dto.CommentDto;
import com.example.automotiveapp.exception.ResourceNotFoundException;
import com.example.automotiveapp.mapper.CommentDtoMapper;
import com.example.automotiveapp.repository.CommentRepository;
import com.example.automotiveapp.repository.ForumRepository;
import com.example.automotiveapp.service.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

// L5 Interface Segregation - second usage
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentSearchService, CommentPersistenceService{
    private final CommentRepository commentRepository;
    private final CommentDtoMapper commentDtoMapper;
    private final PostService postService;
    private final ForumRepository forumRepository;

    public CommentDto saveComment(CommentDto commentDto) {
        Comment comment = commentDtoMapper.map(commentDto);
        comment.setCommentedAt((LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)));

        if (comment.getPost() != null) {
            Post post = comment.getPost();
            post.getComments().add(comment);
            post.setCommentsNumber(post.getCommentsNumber() + 1);
        } else if (comment.getForum() != null) {
            Forum forum = comment.getForum();
            forum.getComments().add(comment);
            forum.setCommentsNumber(forum.getCommentsNumber() + 1);
        }

        Comment savedComment = commentRepository.save(comment);
        return CommentDtoMapper.map(savedComment);
    }

    public Optional<CommentDto> findCommentById(long id) {
        return Optional.ofNullable(commentRepository.findById(id)
                .map(CommentDtoMapper::map).orElseThrow(() -> new ResourceNotFoundException("Nie znaleziono komentarza")));
    }

    public void updateComment(CommentDto commentToUpdate) {
        Comment comment = commentDtoMapper.map(commentToUpdate);
        commentRepository.save(comment);
    }

    public void deleteComment(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nie znaleziono komentarza"));
        if (comment.getPost() != null) {
            comment.getPost().setCommentsNumber(comment.getPost().getCommentsNumber() - 1);
            comment.getPost().getComments().remove(comment);
        } else if (comment.getForum() != null) {
            comment.getForum().setCommentsNumber(comment.getForum().getCommentsNumber() - 1);
            comment.getForum().getComments().remove(comment);
        }
        commentRepository.deleteById(id);
    }

    public List<CommentDto> findCommentsByPostId(Long postId) {
        postService.findPostById(postId);
        return commentRepository.findAllByPostId(postId).stream()
                .map(CommentDtoMapper::map)
                .toList();
    }

    // L3 Iterator - first usage
    public List<CommentDto> findCommentsByForumId(Long forumId) {
        Forum forum = forumRepository.findById(forumId)
                .orElseThrow(() -> new ResourceNotFoundException("Nie znaleziono forum"));

        Set<Comment> commentsSet = forum.getComments();

        CommentCollection collection = new CommentCollection(commentsSet);
        Iterator<Comment> iterator = collection.createIterator();

        List<CommentDto> result = new ArrayList<>();
        while (iterator.hasNext()) {
            Comment comment = iterator.next();
            result.add(CommentDtoMapper.map(comment));
        }

        return result;
    }
}
