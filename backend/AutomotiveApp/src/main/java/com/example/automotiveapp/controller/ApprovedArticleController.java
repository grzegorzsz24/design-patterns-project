package com.example.automotiveapp.controller;

import com.example.automotiveapp.dto.ArticleDto;
import com.example.automotiveapp.reponse.ArticleResponse;
import com.example.automotiveapp.service.article.ArticleServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/user/articles")
@RequiredArgsConstructor
public class ApprovedArticleController {
    private final ArticleServiceInterface articleService;

    @PostMapping
    public ResponseEntity<ArticleDto> addArticle(@RequestBody ArticleDto article) {
        ArticleDto savedArticle = articleService.saveArticle(article);
        URI savedArticleURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedArticle.getId())
                .toUri();
        return ResponseEntity.created(savedArticleURI).body(savedArticle);
    }

    @GetMapping
    public ResponseEntity<ArticleResponse> getAllApprovedArticles(@RequestParam(defaultValue = "") String title,
                                                                  @RequestParam(defaultValue = "1") int page,
                                                                  @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(articleService.findAllApprovedArticles(title, page, size));
    }

    @GetMapping("/{articleId}")
    public ResponseEntity<ArticleDto> getArticle(@PathVariable Long articleId) {
        return ResponseEntity.ok(articleService.findArticleById(articleId));
    }

    @GetMapping("/own")
    public ResponseEntity<ArticleResponse> getMyArticles(@RequestParam(defaultValue = "1") int page,
                                                         @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(articleService.findMyArticles(page, size));
    }
}
