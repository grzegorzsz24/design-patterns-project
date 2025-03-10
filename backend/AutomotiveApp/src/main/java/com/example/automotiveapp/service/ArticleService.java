package com.example.automotiveapp.service;

import com.example.automotiveapp.domain.article.Article;
import com.example.automotiveapp.domain.User.User;
import com.example.automotiveapp.domain.article.ArticleFactoryProvider;
import com.example.automotiveapp.dto.ArticleDto;
import com.example.automotiveapp.exception.BadRequestException;
import com.example.automotiveapp.exception.ResourceNotFoundException;
import com.example.automotiveapp.mapper.ArticleDtoMapper;
import com.example.automotiveapp.reponse.ArticleResponse;
import com.example.automotiveapp.repository.ArticleRepository;
import com.example.automotiveapp.repository.LikeRepository;
import com.example.automotiveapp.repository.SavedArticleRepository;
import com.example.automotiveapp.repository.UserRepository;
import com.example.automotiveapp.service.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final ArticleDtoMapper articleDtoMapper;
    private final LikeRepository likeRepository;
    private final SavedArticleRepository savedArticleRepository;
    private final UserRepository userRepository;
    private final ArticleFactoryProvider articleFactoryProvider;

    public ArticleDto saveArticle(ArticleDto articleDto) {
        if (articleRepository.findByTitle(articleDto.getTitle()).isPresent()) {
            throw new BadRequestException("Artykuł o podanym tytule już istnieje!");
        }

        User user = userRepository.findByEmail(SecurityUtils.getCurrentUserEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Nie znaleziono użytkownika"));

        Article article = articleFactoryProvider.provide(false).create(
                null,
                articleDto.getTitle(),
                articleDto.getContent(),
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS),
                false,
                0,
                user,
                false
        );

        Article savedArticle = articleRepository.save(article);

        return ArticleDtoMapper.map(savedArticle);
    }

    public void updateArticle(ArticleDto articleToUpdate) {
        Article existingArticle = articleRepository.findById(articleToUpdate.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Nie znaleziono artykułu"));

        boolean wantApproved = existingArticle.isApproved();

        Article updated = articleFactoryProvider.provide(wantApproved).create(
                existingArticle.getId(),
                articleToUpdate.getTitle(),
                articleToUpdate.getContent(),
                existingArticle.getPublishedAt(),
                existingArticle.isLiked(),
                existingArticle.getLikesNumber(),
                existingArticle.getUser(),
                wantApproved
        );

        articleRepository.save(updated);
    }

    public ArticleDto findArticleById(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nie znaleziono artykułu"));
        ArticleDto articleDto = ArticleDtoMapper.map(article);
        articleDto.setLiked(likeRepository.getLikeByUser_EmailAndArticleId(SecurityUtils.getCurrentUserEmail(), article.getId()).isPresent());
        articleDto.setSaved(savedArticleRepository.findByUserEmailAndArticle_Id(SecurityUtils.getCurrentUserEmail(), article.getId()).isPresent());
        articleDto.setUser(article.getUser().getNickname());
        return articleDto;
    }

    public ArticleResponse findAllApprovedArticles(String title, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        long totalApprovedArticles = articleRepository.countAllByApprovedIsTrue();
        List<Article> articles = articleRepository.findByTitleContainsIgnoreCaseAndApprovedIsTrueOrderByPublishedAtDesc(title, pageable);
        List<ArticleDto> articleDtos = new ArrayList<>();
        setArticlesLikesAndSavedStatus(articles, articleDtos);
        return new ArticleResponse(articleDtos, totalApprovedArticles);
    }

    private void setArticlesLikesAndSavedStatus(List<Article> articles, List<ArticleDto> articleDtos) {
        for (Article article : articles) {
            ArticleDto articleDto = ArticleDtoMapper.map(article);
            articleDto.setLiked(likeRepository.getLikeByUser_EmailAndArticleId(SecurityUtils.getCurrentUserEmail(), article.getId()).isPresent());
            articleDto.setSaved(savedArticleRepository.findByUserEmailAndArticle_Id(SecurityUtils.getCurrentUserEmail(), article.getId()).isPresent());
            articleDtos.add(articleDto);
        }
    }

    public ArticleResponse findMyArticles(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        long totalApprovedArticles = articleRepository.countAllByApprovedIsTrue();
        List<Article> articles = articleRepository.findAllByUserEmail(SecurityUtils.getCurrentUserEmail(), pageable);
        List<ArticleDto> articleDtos = new ArrayList<>();
        setArticlesLikesAndSavedStatus(articles, articleDtos);
        return new ArticleResponse(articleDtos, totalApprovedArticles);
    }

    public ArticleResponse findAllNotApprovedArticles(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        long totalNotApprovedArticles = articleRepository.countAllByApprovedIsFalse();
        List<Article> articles = articleRepository.findAllByApprovedIsFalseOrderByPublishedAtDesc(pageable);
        List<ArticleDto> articleDtos = new ArrayList<>();
        setArticlesLikesAndSavedStatus(articles, articleDtos);
        return new ArticleResponse(articleDtos, totalNotApprovedArticles);
    }

    public void setArticleApproved(Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException("Nie znaleziono artykułu"));
        Article approved = articleFactoryProvider.provide(true).create(
                article.getId(),
                article.getTitle(),
                article.getContent(),
                article.getPublishedAt(),
                article.isLiked(),
                article.getLikesNumber(),
                article.getUser(),
                true
        );

        articleRepository.save(approved);
    }

    public void deleteArticleById(Long articleId) {
        articleRepository.deleteById(articleId);
    }
}

