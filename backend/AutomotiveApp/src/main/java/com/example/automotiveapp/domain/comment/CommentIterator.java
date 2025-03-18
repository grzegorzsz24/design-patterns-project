package com.example.automotiveapp.domain.comment;

import com.example.automotiveapp.domain.Comment;

import java.util.Iterator;
import java.util.Set;

// L3 Iterator - first impl
public class CommentIterator implements Iterator<Comment> {
    private final Iterator<Comment> internalIterator;

    public CommentIterator(Set<Comment> comments) {
        this.internalIterator = comments.iterator();
    }

    @Override
    public boolean hasNext() {
        return internalIterator.hasNext();
    }

    @Override
    public Comment next() {
        return internalIterator.next();
    }
}
