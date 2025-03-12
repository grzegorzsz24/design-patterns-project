package com.example.automotiveapp.config;

import com.example.automotiveapp.service.article.ArticleService;
import com.example.automotiveapp.service.article.ArticleServiceInterface;
import com.example.automotiveapp.service.article.FallbackArticleServiceDecorator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class ArticleServiceConfig {

    @Bean(name = "decoratedArticleService")
    @Primary
    public ArticleServiceInterface articleService(ArticleService originalArticleService) {
        return new FallbackArticleServiceDecorator(originalArticleService);
    }
}
