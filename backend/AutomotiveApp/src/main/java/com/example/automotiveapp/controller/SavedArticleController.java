package com.example.automotiveapp.controller;

import com.example.automotiveapp.dto.ArticleDto;
import com.example.automotiveapp.reponse.ApiResponse;
import com.example.automotiveapp.service.FindSavedArticleService;
import com.example.automotiveapp.service.SavedArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// L4 Open - Closed - first impl
@RestController
@RequestMapping("/user/articles/saved")
@RequiredArgsConstructor
public class SavedArticleController {
    private final SavedArticleService savedArticleService;
    private final FindSavedArticleService findSavedArticleService;

    @PostMapping
    public ResponseEntity<ApiResponse> addToSavedArticles(@RequestParam Long articleId) {
        savedArticleService.saveArticle(articleId);
        return ResponseEntity.ok(new ApiResponse("Artykuł został zapisany", HttpStatus.OK));
    }

    @GetMapping
    public ResponseEntity<List<ArticleDto>> getSavedArticles(@RequestParam(defaultValue = "1") int page,
                                                             @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(findSavedArticleService.findSavedArticles(page, size));
    }
}
