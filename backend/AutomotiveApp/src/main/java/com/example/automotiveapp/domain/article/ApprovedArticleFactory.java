package com.example.automotiveapp.domain.article;

import com.example.automotiveapp.domain.User.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ApprovedArticleFactory extends ArticleFactory {

    @Override
    public Article createArticle(Long id, String title, String content, LocalDateTime publishedAt, boolean isLiked, int likesNumber, User user, boolean approved) {
        return new ApprovedArticle(id, title, content, publishedAt, isLiked, likesNumber, user);
    }
}
