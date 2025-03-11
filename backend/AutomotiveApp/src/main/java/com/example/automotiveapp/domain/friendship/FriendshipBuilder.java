package com.example.automotiveapp.domain.friendship;

import com.example.automotiveapp.domain.User.User;

// L1 Builder - third impl
public class FriendshipBuilder {
    private Long id;
    private User user1;
    private User user2;

    public FriendshipBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public FriendshipBuilder user1(User user1) {
        this.user1 = user1;
        return this;
    }

    public FriendshipBuilder user2(User user2) {
        this.user2 = user2;
        return this;
    }

    public Friendship build() {
        Friendship friendship = new Friendship();
        friendship.setId(id);
        friendship.setUser1(user1);
        friendship.setUser2(user2);
        return friendship;
    }
}

