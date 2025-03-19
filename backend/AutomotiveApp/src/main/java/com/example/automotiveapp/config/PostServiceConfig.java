package com.example.automotiveapp.config;

import com.example.automotiveapp.service.post.AuthorizationPostServiceDecorator;
import com.example.automotiveapp.service.post.PostPersistenceService;
import com.example.automotiveapp.service.post.PostSearchService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
class PostServiceConfig {

    @Bean(name = "decoratedPostService")
    @Primary
    public AuthorizationPostServiceDecorator postService(PostSearchService searchService, PostPersistenceService persistenceService) {
        return new AuthorizationPostServiceDecorator(searchService, persistenceService);
    }
}
