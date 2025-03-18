package com.example.automotiveapp.service.article;

import com.example.automotiveapp.reponse.ArticleResponse;

public interface FindAllApprovedArticlesService {

    ArticleResponse findAllApprovedArticles(String title, int page, int size);
}
