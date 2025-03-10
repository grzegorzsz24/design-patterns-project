package com.example.automotiveapp.controller;

import com.example.automotiveapp.domain.request.PostSaveRequest;
import com.example.automotiveapp.dto.PostDto;
import com.example.automotiveapp.dto.ReportDto;
import com.example.automotiveapp.reponse.PostResponse;
import com.example.automotiveapp.service.PostService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/user/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final ObjectMapper objectMapper;

    @PostMapping
    public ResponseEntity<PostDto> addPost(@Valid @ModelAttribute PostSaveRequest post) {
        PostDto savedPost = postService.savePost(post);
        URI savedPostURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPost.getId())
                .toUri();
        return ResponseEntity.created(savedPostURI).body(savedPost);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updatePost(@PathVariable Long id, @RequestBody JsonMergePatch patch) {
        try {
            PostDto postDto = postService.findPostById(id);
            PostDto postPatched = applyPatch(postDto, patch);
            postService.updatePost(postPatched);

        } catch (JsonPatchException | JsonProcessingException e) {
            return ResponseEntity.internalServerError().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    private PostDto applyPatch(PostDto postDto, JsonMergePatch patch) throws JsonPatchException, JsonProcessingException {
        JsonNode postNode = objectMapper.valueToTree(postDto);
        JsonNode postPatchedNode = patch.apply(postNode);
        return objectMapper.treeToValue(postPatchedNode, PostDto.class);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Long postId) {
        return ResponseEntity.ok(postService.findPostById(postId));
    }

    @GetMapping("/friends")
    public ResponseEntity<PostResponse> getFriendsPosts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return ResponseEntity.ok(postService.getFriendsPosts(pageable));
    }


    @GetMapping("/user")
    public ResponseEntity<PostResponse> getUserPosts(
            @RequestParam Long userId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return ResponseEntity.ok(postService.getUserPosts(userId, pageable));
    }

    @PostMapping("/report")
    public ResponseEntity<ReportDto> reportPost(@RequestBody ReportDto reportDto) {
        return ResponseEntity.ok(postService.reportPost(reportDto));
    }
}
