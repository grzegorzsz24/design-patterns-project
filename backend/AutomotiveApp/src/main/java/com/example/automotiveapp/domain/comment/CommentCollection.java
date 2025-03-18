package com.example.automotiveapp.domain.comment;

import com.example.automotiveapp.domain.Comment;
import com.example.automotiveapp.domain.IterableCollection;

import java.util.Iterator;
import java.util.Set;

public class CommentCollection implements IterableCollection<Comment> {
    private final Set<Comment> comments;

    public CommentCollection(Set<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public Iterator<Comment> createIterator() {
        return new CommentIterator(comments);
    }
}
