package com.example.automotiveapp.domain;

// L2 Flyweight - third impl
public record UserFriendshipStatus(String status) {

    public static UserFriendshipStatus FRIENDS = new UserFriendshipStatus("FRIENDS");
    public static UserFriendshipStatus INVITATION_SENT = new UserFriendshipStatus("INVITATION_SENT");
    public static UserFriendshipStatus INVITATION_RECEIVED = new UserFriendshipStatus("INVITATION_RECEIVED");
    public static UserFriendshipStatus NOT_FRIENDS = new UserFriendshipStatus("NOT_FRIENDS");
}
