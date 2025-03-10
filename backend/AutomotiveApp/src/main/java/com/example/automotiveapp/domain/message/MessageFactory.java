package com.example.automotiveapp.domain.message;

import com.example.automotiveapp.domain.Channel;
import com.example.automotiveapp.domain.User.User;

import java.time.LocalDateTime;

public class MessageFactory {

    private MessageFactory() {
    }

    public static Message createTextMessage(Channel channel, User sender, String content,
                                            User receiver, LocalDateTime createdAt) {
        Message message = new Message();
        message.setChannel(channel);
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setMessage(content);
        message.setCreatedAt(createdAt);
        return message;
    }
}
