package com.example.automotiveapp.domain.article;

import com.example.automotiveapp.domain.User.User;

import java.time.LocalDateTime;

// L1 Factory - first impl
// L5 Liskov Substitution - first impl
public abstract class ArticleFactory {

    @SuppressWarnings("checkstyle:ParameterNumber")
    public Article create(
            Long id,
            String title,
            String content,
            LocalDateTime publishedAt,
            boolean isLiked,
            int likesNumber,
            User user,
            boolean approved
    ) {
        Article article = createArticle(id, title, content, publishedAt, isLiked, likesNumber, user, approved);
        System.out.println(article);
        return article;
    }

    @SuppressWarnings("checkstyle:ParameterNumber")
    public abstract Article createArticle(
            Long id,
            String title,
            String content,
            LocalDateTime publishedAt,
            boolean isLiked,
            int likesNumber,
            User user,
            boolean approved
    );

    // L1 Prototype - first impl
    public abstract ArticleFactory clone();

}
