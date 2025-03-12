package com.example.automotiveapp.config;

import com.example.automotiveapp.service.post.AuthorizationPostServiceDecorator;
import com.example.automotiveapp.service.post.PostService;
import com.example.automotiveapp.service.post.PostServiceInterface;
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
