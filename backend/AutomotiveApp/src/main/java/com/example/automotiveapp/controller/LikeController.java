package com.example.automotiveapp.controller;

import com.example.automotiveapp.dto.LikeDto;
import com.example.automotiveapp.request.LikeRequest;
import com.example.automotiveapp.request.adapter.LikeRequestAdapter;
import com.example.automotiveapp.service.like.LikeService;
import com.example.automotiveapp.service.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/user/likes")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;
    private final PostService postService;

    @PostMapping
    public ResponseEntity<LikeDto> addLike(@RequestBody LikeRequest like) {
        LikeDto savedLike = likeService.saveLike(new LikeRequestAdapter(like));
        URI savedLikeURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedLike.getId())
                .toUri();
        return ResponseEntity.created(savedLikeURI).body(savedLike);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<List<LikeDto>> getAllPostLikes(@PathVariable Long postId) {
        postService.findPostById(postId);
        return ResponseEntity.ok(likeService.getPostLikes(postId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLike(@PathVariable Long id) {
        likeService.deleteLike(id);
        return ResponseEntity.noContent().build();
    }
}
