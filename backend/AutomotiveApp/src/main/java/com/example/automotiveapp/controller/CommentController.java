package com.example.automotiveapp.controller;

import com.example.automotiveapp.dto.CommentDto;
import com.example.automotiveapp.service.comment.CommentServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/user/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentServiceImpl commentService;
    private final ObjectMapper objectMapper;

    @PostMapping
    public ResponseEntity<CommentDto> addComment(@RequestBody CommentDto comment) {
        CommentDto savedComment = commentService.saveComment(comment);
        URI savedCommentURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedComment.getId())
                .toUri();
        return ResponseEntity.created(savedCommentURI).body(savedComment);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateComment(@PathVariable Long id, @RequestBody JsonMergePatch patch) {
        try {
            CommentDto commentDto = commentService.findCommentById(id).orElseThrow();
            CommentDto commentPatched = applyPatch(commentDto, patch);
            commentService.updateComment(commentPatched);

        } catch (JsonPatchException | JsonProcessingException e) {
            return ResponseEntity.internalServerError().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/post")
    public ResponseEntity<List<CommentDto>> getPostComments(@RequestParam Long postId) {
        return ResponseEntity.ok(commentService.findCommentsByPostId(postId));
    }

    private CommentDto applyPatch(CommentDto commentDto, JsonMergePatch patch) throws JsonPatchException, JsonProcessingException {
        JsonNode commentNode = objectMapper.valueToTree(commentDto);
        JsonNode commentPatchedNode = patch.apply(commentNode);
        return objectMapper.treeToValue(commentPatchedNode, CommentDto.class);
    }

    @GetMapping("/forum/{forumId}")
    public ResponseEntity<List<CommentDto>> getForumComments(@PathVariable Long forumId) {
        return ResponseEntity.ok(commentService.findCommentsByForumId(forumId));
    }
}
