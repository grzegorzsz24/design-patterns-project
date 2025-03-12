package com.example.automotiveapp.config;

import com.example.automotiveapp.service.AuthorizationPostServiceDecorator;
import com.example.automotiveapp.service.PostService;
import com.example.automotiveapp.service.PostServiceInterface;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
class PostServiceConfig {

    @Bean(name = "decoratedPostService")
    @Primary
    public PostServiceInterface postService(PostService originalPostService) {
        return new AuthorizationPostServiceDecorator(originalPostService);
    }
}
