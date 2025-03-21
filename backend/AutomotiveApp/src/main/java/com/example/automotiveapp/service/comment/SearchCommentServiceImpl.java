package com.example.automotiveapp.service.comment;

import com.example.automotiveapp.domain.Comment;
import com.example.automotiveapp.domain.comment.CommentCollection;
import com.example.automotiveapp.domain.forum.Forum;
import com.example.automotiveapp.dto.CommentDto;
import com.example.automotiveapp.exception.ResourceNotFoundException;
import com.example.automotiveapp.mapper.CommentDtoMapper;
import com.example.automotiveapp.repository.CommentRepository;
import com.example.automotiveapp.repository.ForumRepository;
import com.example.automotiveapp.service.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

// L4 Single responsibility - third impl
@Service
@RequiredArgsConstructor
public class SearchCommentServiceImpl implements CommentSearchService {
    private final CommentRepository commentRepository;
    private final PostService postService;
    private final ForumRepository forumRepository;

    public Optional<CommentDto> findCommentById(long id) {
        return Optional.ofNullable(commentRepository.findById(id)
                .map(CommentDtoMapper::map).orElseThrow(() -> new ResourceNotFoundException("Nie znaleziono komentarza")));
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
