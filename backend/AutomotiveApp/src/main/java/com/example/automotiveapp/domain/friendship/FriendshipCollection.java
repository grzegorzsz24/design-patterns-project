package com.example.automotiveapp.domain.friendship;

import com.example.automotiveapp.domain.IterableCollection;
import com.example.automotiveapp.domain.User.User;

import java.util.Iterator;
import java.util.List;

public class FriendshipCollection implements IterableCollection<User> {
    private final List<User> friends;

    public FriendshipCollection(List<User> friends) {
        this.friends = friends;
    }

    @Override
    public Iterator<User> createIterator() {
        return new FriendIterator(this.friends);
    }
}
