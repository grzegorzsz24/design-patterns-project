package com.example.automotiveapp.service.article;

import com.example.automotiveapp.reponse.ArticleResponse;

public interface FindMyArticlesService {

    ArticleResponse findMyArticles(int page, int size);
}
