package com.example.automotiveapp.domain.message;

import com.example.automotiveapp.domain.Channel;
import com.example.automotiveapp.domain.User.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ConcreteMessageFactory extends MessageFactory {

    @Override
    public Message createTextMessage(Channel channel, User sender, String content,
                                              User receiver, LocalDateTime createdAt) {
        ConcreteMessage message = new ConcreteMessage();
        message.setChannel(channel);
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setMessage(content);
        message.setCreatedAt(createdAt);
        return message;
    }
}
