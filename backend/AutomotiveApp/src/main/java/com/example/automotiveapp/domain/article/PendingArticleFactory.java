package com.example.automotiveapp.domain.article;

import com.example.automotiveapp.domain.User.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PendingArticleFactory extends ArticleFactory {
    @Override
    public Article createArticle(Long id, String title, String content, LocalDateTime publishedAt, boolean isLiked, int likesNumber, User user, boolean approved) {
        return new PendingArticle(id, title, content, publishedAt, isLiked, likesNumber, user);
    }
}
