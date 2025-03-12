package com.example.automotiveapp.service;

import com.example.automotiveapp.domain.Forum;
import lombok.Getter;

@Getter
public class ForumContent implements ContentComponent {
    private final Forum forum;

    public ForumContent(Forum forum) {
        this.forum = forum;
    }

    @Override
    public String getTitle() {
        return forum.getTitle();
    }

    @Override
    public String getSummary() {
        return forum.getContent();
    }

}

