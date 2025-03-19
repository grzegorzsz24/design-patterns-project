package com.example.automotiveapp.service.forum;

import com.example.automotiveapp.domain.forum.Forum;
import com.example.automotiveapp.service.ContentComponent;
import com.example.automotiveapp.service.ContentVisitor;
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

    @Override
    public void accept(ContentVisitor visitor) {
        visitor.visit(this);
    }

}

