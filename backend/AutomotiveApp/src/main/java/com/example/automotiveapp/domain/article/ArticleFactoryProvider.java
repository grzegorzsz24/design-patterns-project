package com.example.automotiveapp.domain.article;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ArticleFactoryProvider {

    private final ApprovedArticleFactory approvedArticleFactory;
    private final PendingArticleFactory pendingArticleFactory;

    public ArticleFactory provide(boolean approved) {
        return approved ? approvedArticleFactory : pendingArticleFactory;
    }
}
