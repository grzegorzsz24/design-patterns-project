package com.example.automotiveapp.service.post;

import com.example.automotiveapp.domain.Post;
import com.example.automotiveapp.service.ContentComponent;
import com.example.automotiveapp.service.ContentVisitor;
import lombok.Getter;

@Getter
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

    @Override
    public void accept(ContentVisitor visitor) {
        visitor.visit(this);
    }
}
