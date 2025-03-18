package com.example.automotiveapp.domain.article;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ArticleFactoryProvider {

    private final ApprovedArticleFactory approvedArticleFactory;
    private final PendingArticleFactory pendingArticleFactory;

    // start L5 Liskov Substitution - first usage
    public ArticleFactory provide(boolean approved) {
        // start L1 Prototype - first usage
        return approved ? approvedArticleFactory.clone() : pendingArticleFactory.clone();
    }
}
