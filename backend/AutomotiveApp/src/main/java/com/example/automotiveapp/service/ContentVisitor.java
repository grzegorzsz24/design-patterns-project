package com.example.automotiveapp.service;

import com.example.automotiveapp.service.forum.ForumContent;
import com.example.automotiveapp.service.post.PostContent;

// L3 Visitor - second impl
public interface ContentVisitor {
    void visit(ContentFeed feed);
    void visit(ForumContent forum);
    void visit(PostContent article);
}
