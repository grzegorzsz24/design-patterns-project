package com.example.automotiveapp.service;

import com.example.automotiveapp.domain.SavedArticle;
import com.example.automotiveapp.domain.article.Article;
import com.example.automotiveapp.dto.ArticleDto;
import com.example.automotiveapp.exception.ResourceNotFoundException;
import com.example.automotiveapp.mapper.ArticleDtoMapper;
import com.example.automotiveapp.repository.LikeRepository;
import com.example.automotiveapp.repository.SavedArticleRepository;
import com.example.automotiveapp.repository.UserRepository;
import com.example.automotiveapp.service.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


// L4 Single responsibility - first impl
@RequiredArgsConstructor
@Service
public class FindSavedArticleService {
    private final UserRepository userRepository;
    private final SavedArticleRepository savedArticleRepository;
    private final LikeRepository likeRepository;

    public List<ArticleDto> findSavedArticles(int page, int size) {
        Long userId = userRepository.findByEmail(SecurityUtils.getCurrentUserEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Nie znaleziono użytkownika")).getId();
        Pageable pageable = PageRequest.of(page - 1, size);
        List<SavedArticle> savedArticles = savedArticleRepository.findAllByUserId(userId, pageable);
        List<Article> articles = new ArrayList<>();
        List<ArticleDto> articleDtos = new ArrayList<>();
        savedArticles.forEach(val -> articles.add(val.getArticle()));
        setArticlesLikesAndSavedStatus(articles, articleDtos);
        return articleDtos;
    }

    private void setArticlesLikesAndSavedStatus(List<Article> articles, List<ArticleDto> articleDtos) {
        for (Article article : articles) {
            ArticleDto articleDto = ArticleDtoMapper.map(article);
            articleDto.setLiked(likeRepository.getLikeByUser_EmailAndArticleId(SecurityUtils.getCurrentUserEmail(), article.getId()).isPresent());
            articleDto.setSaved(savedArticleRepository.findByUserEmailAndArticle_Id(SecurityUtils.getCurrentUserEmail(), article.getId()).isPresent());
            articleDtos.add(articleDto);
        }
    }
}
