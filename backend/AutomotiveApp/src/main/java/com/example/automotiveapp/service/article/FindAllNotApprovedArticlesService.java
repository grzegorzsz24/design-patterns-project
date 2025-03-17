package com.example.automotiveapp.service.article;

import com.example.automotiveapp.reponse.ArticleResponse;

public interface FindAllNotApprovedArticlesService {

    ArticleResponse findAllNotApprovedArticles(int page, int size);
}
