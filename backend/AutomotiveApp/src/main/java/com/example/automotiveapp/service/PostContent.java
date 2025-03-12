package com.example.automotiveapp.service;

import com.example.automotiveapp.domain.Post;

public class PostContent implements ContentComponent {

    private final Post post;

    public PostContent(Post post) {
        this.post = post;
    }

    @Override
    public String getTitle() {
        return "Post from " + post.getUser().getNickname();
    }

    @Override
    public String getSummary() {
        return post.getContent();
    }

    public Post getPost() {
        return post;
    }
}
