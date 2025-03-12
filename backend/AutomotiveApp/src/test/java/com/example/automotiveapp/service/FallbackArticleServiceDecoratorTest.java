package com.example.automotiveapp.service;

import com.example.automotiveapp.dto.ArticleDto;
import com.example.automotiveapp.service.article.ArticleService;
import com.example.automotiveapp.service.article.FallbackArticleServiceDecorator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FallbackArticleServiceDecoratorTest {

    @Test
    void testFallbackWhenDelegateFails() {
        ArticleService delegate = mock(ArticleService.class);

        ArticleDto article = new ArticleDto();
        article.setId(1L);
        article.setTitle("Test Article");

        when(delegate.saveArticle(any())).thenReturn(article);
        when(delegate.findArticleById(1L))
                .thenReturn(article)
                .thenThrow(new RuntimeException("Simulated failure"));

        FallbackArticleServiceDecorator decorator = new FallbackArticleServiceDecorator(delegate);

        ArticleDto savedArticle = decorator.saveArticle(article);
        assertEquals(article, savedArticle);

        ArticleDto fetchedArticle1 = decorator.findArticleById(1L);
        assertEquals(article, fetchedArticle1);

        ArticleDto fetchedArticle2 = decorator.findArticleById(1L);
        assertEquals(article, fetchedArticle2);
    }
}
