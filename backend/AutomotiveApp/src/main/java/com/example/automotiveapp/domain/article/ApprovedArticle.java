package com.example.automotiveapp.domain.article;

import com.example.automotiveapp.domain.User.User;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;

@Entity
@DiscriminatorValue("APPROVED")
@NoArgsConstructor
public class ApprovedArticle extends Article {

    public ApprovedArticle(
            Long id,
            String title,
            String content,
            LocalDateTime publishedAt,
            boolean isLiked,
            int likesNumber,
            User user
    ) {
        super(id, title, content, publishedAt, isLiked, likesNumber, true, user, new HashSet<>());
    }

    @Override
    public boolean isApproved() {
        return true;
    }
}

