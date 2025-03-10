package com.example.automotiveapp.domain.message;

import com.example.automotiveapp.domain.Channel;
import com.example.automotiveapp.domain.User.User;

import java.time.LocalDateTime;

public abstract class MessageFactory {

    public Message create(Channel channel, User sender, String content,
                          User receiver, LocalDateTime createdAt) {
        Message message = createTextMessage(channel, sender, content, receiver, createdAt);
        System.out.println(message);
        return message;
    }

    public abstract Message createTextMessage(Channel channel, User sender, String content,
                                              User receiver, LocalDateTime createdAt);
}
