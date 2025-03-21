package com.example.automotiveapp.controller;

import com.example.automotiveapp.dto.ForumDto;
import com.example.automotiveapp.reponse.ApiResponse;
import com.example.automotiveapp.service.FindSavedForumsService;
import com.example.automotiveapp.service.SavedForumService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user/forums/saved")
@RequiredArgsConstructor
public class SavedForumController {
    private final SavedForumService savedForumService;
    private final FindSavedForumsService findSavedForumsService;

    @PostMapping
    public ResponseEntity<ApiResponse> addToSavedForums(@RequestParam Long forumId) {
        savedForumService.saveForum(forumId);
        return ResponseEntity.ok(new ApiResponse("Forum zostało zapisany", HttpStatus.OK));
    }

    @GetMapping
    public ResponseEntity<List<ForumDto>> getSavedForums(@RequestParam(defaultValue = "1") int page,
                                                         @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(findSavedForumsService.findSavedForums(page, size));
    }
}
