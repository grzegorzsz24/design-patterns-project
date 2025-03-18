package com.example.automotiveapp.service;

import lombok.RequiredArgsConstructor;

// L3 Command - third impl
@RequiredArgsConstructor
public class RemoveFriendCommand implements Command {

    private final FriendshipService friendshipService;
    private final Long friendId;

    @Override
    public void execute() {
        friendshipService.removeFriend(friendId);
    }
}
