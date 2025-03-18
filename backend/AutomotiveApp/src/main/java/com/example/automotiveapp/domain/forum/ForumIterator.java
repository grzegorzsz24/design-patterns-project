package com.example.automotiveapp.domain.forum;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

// L3 Iterator - second impl
public class ForumIterator implements Iterator<Forum> {
    private final List<Forum> forums;
    private int position = 0;

    public ForumIterator(List<Forum> forums) {
        this.forums = forums;
    }

    @Override
    public boolean hasNext() {
        return position < forums.size();
    }

    @Override
    public Forum next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return forums.get(position++);
    }
}
