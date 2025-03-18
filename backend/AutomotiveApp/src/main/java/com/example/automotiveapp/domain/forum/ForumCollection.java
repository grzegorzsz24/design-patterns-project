package com.example.automotiveapp.domain.forum;

import com.example.automotiveapp.domain.IterableCollection;

import java.util.Iterator;
import java.util.List;

public class ForumCollection implements IterableCollection<Forum> {
    private final List<Forum> forums;

    public ForumCollection(List<Forum> forums) {
        this.forums = forums;
    }

    @Override
    public Iterator<Forum> createIterator() {
        return new ForumIterator(forums);
    }
}
