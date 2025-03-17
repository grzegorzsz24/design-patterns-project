package com.example.automotiveapp.service.article;

import com.example.automotiveapp.dto.ArticleDto;

import com.example.automotiveapp.reponse.ArticleResponse;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// L2 Decorator - second implementation
public class FallbackArticleServiceDecorator implements ArticleServiceInterface {

    private final ArticleService delegate;
    private final Map<Long, ArticleDto> cache = new ConcurrentHashMap<>();

    public FallbackArticleServiceDecorator(ArticleService delegate) {
        this.delegate = delegate;
    }

    @Override
    public ArticleDto saveArticle(ArticleDto articleDto) {
        ArticleDto savedArticle = delegate.saveArticle(articleDto);
        cache.put(savedArticle.getId(), savedArticle);
        return savedArticle;
    }

    @Override
    public ArticleDto findArticleById(Long id) {
        try {
            ArticleDto article = delegate.findArticleById(id);
            cache.put(id, article);
            return article;
        } catch (Exception ex) {
            ArticleDto fallback = cache.get(id);
            if (fallback != null) {
                return fallback;
            }
            throw ex;
        }
    }

    @Override
    public ArticleResponse findAllApprovedArticles(String title, int page, int size) {
        return delegate.findAllApprovedArticles(title, page, size);
    }

    @Override
    public ArticleResponse findMyArticles(int page, int size) {
        return delegate.findMyArticles(page, size);
    }

    @Override
    public ArticleResponse findAllNotApprovedArticles(int page, int size) {
        return delegate.findAllNotApprovedArticles(page, size);
    }

    @Override
    public void setArticleApproved(Long articleId) {
        delegate.setArticleApproved(articleId);
    }

    @Override
    public void deleteArticleById(Long articleId) {
        delegate.deleteArticleById(articleId);
        cache.remove(articleId);
    }
}