package com.example.automotiveapp.repository.message;

import com.example.automotiveapp.domain.message.ConcreteMessage;

import java.util.List;

public interface MessageRepository {
    List<ConcreteMessage> findAllByChannelId(Long channelId);

    ConcreteMessage save(ConcreteMessage message);
}
