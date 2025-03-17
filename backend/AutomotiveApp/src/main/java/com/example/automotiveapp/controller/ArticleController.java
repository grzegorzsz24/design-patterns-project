package com.example.automotiveapp.controller;

import com.example.automotiveapp.reponse.ArticleResponse;
import com.example.automotiveapp.service.article.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/articles/pending")
@RequiredArgsConstructor
public class ArticleController {
    // L2 Decorator - second usage
    private final ArticleService articleService;

    @GetMapping
    public ResponseEntity<ArticleResponse> getNotApprovedArticles(@RequestParam(defaultValue = "1") int page,
                                                                  @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(articleService.findAllNotApprovedArticles(page, size));
    }

    @PostMapping
    public ResponseEntity<?> approveArticle(@RequestParam Long articleId) {
        articleService.setArticleApproved(articleId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<?> deleteArticle(@RequestParam Long articleId) {
        articleService.deleteArticleById(articleId);
        return ResponseEntity.noContent().build();
    }
}
