package com.example.automotiveapp.domain.article;

import com.example.automotiveapp.domain.User.User;

import java.time.LocalDateTime;

public class ArticleFactory {

    public static Article createArticle(
            Long id,
            String title,
            String content,
            LocalDateTime publishedAt,
            boolean isLiked,
            int likesNumber,
            User user,
            boolean approved
    ) {
        if (approved) {
            return new ApprovedArticle(id, title, content, publishedAt, isLiked, likesNumber, user);
        } else {
            return new PendingArticle(id, title, content, publishedAt, isLiked, likesNumber, user);
        }
    }
}
