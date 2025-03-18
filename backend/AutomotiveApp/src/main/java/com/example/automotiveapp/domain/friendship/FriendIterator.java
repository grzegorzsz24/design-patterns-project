package com.example.automotiveapp.domain.friendship;

import com.example.automotiveapp.domain.User.User;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

// L3 Iterator - third impl
public class FriendIterator implements Iterator<User> {
    private final List<User> friends;
    private int position = 0;

    public FriendIterator(List<User> friends) {
        this.friends = friends;
    }

    @Override
    public boolean hasNext() {
        return position < friends.size();
    }

    @Override
    public User next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return friends.get(position++);
    }
}
