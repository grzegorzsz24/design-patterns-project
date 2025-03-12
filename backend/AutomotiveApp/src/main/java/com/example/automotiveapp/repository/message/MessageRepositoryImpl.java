package com.example.automotiveapp.repository.message;

import com.example.automotiveapp.domain.message.ConcreteMessage;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class MessageRepositoryImpl implements MessageRepository {

    private final MessageJpaRepository messageJpaRepository;

    @Override
    public List<ConcreteMessage> findAllByChannelId(Long channelId) {
        return messageJpaRepository.findAllByChannelId(channelId);
    }

    @Override
    public ConcreteMessage save(ConcreteMessage message) {
        return messageJpaRepository.save(message);
    }
}
