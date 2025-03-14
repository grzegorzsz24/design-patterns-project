package com.example.automotiveapp.domain;

import com.example.automotiveapp.domain.User.User;
import com.example.automotiveapp.domain.article.Article;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class SavedArticle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;
}
