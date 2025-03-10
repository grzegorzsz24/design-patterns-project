package com.example.automotiveapp.domain.article;

import com.example.automotiveapp.domain.User.User;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;

@Entity
@DiscriminatorValue("PENDING")
@NoArgsConstructor
public class PendingArticle extends Article {

    public PendingArticle(
            Long id,
            String title,
            String content,
            LocalDateTime publishedAt,
            boolean isLiked,
            int likesNumber,
            User user
    ) {
        super(id, title, content, publishedAt, isLiked, likesNumber, false, user, new HashSet<>());
    }

    @Override
    public boolean isApproved() {
        return false;
    }
}

