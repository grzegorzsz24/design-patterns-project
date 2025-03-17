package com.example.automotiveapp.service.article;

import com.example.automotiveapp.dto.ArticleDto;
import com.example.automotiveapp.reponse.ArticleResponse;

public interface ArticleServiceInterface {

    ArticleDto saveArticle(ArticleDto articleDto);

    ArticleDto findArticleById(Long id);

    ArticleResponse findAllApprovedArticles(String title, int page, int size);

    ArticleResponse findMyArticles(int page, int size);

    ArticleResponse findAllNotApprovedArticles(int page, int size);

    void setArticleApproved(Long articleId);

    void deleteArticleById(Long articleId);
}
