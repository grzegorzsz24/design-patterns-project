package com.example.automotiveapp.repository.message;

import com.example.automotiveapp.domain.message.ConcreteMessage;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

// L2 Proxy - second impl
@AllArgsConstructor
public class MessageCacheProxy implements MessageRepository {

    private final MessageRepository messageRepository;
    private final HashMap<Long, ConcreteMessage> cache = new HashMap<>();

    @Override
    public List<ConcreteMessage> findAllByChannelId(Long channelId) {
        List<ConcreteMessage> messages = cache.values().stream()
                .filter(message -> message.getChannel().getId().equals(channelId))
                .toList();

        if (!messages.isEmpty()) {
            return messages;
        }

        List<ConcreteMessage> allByChannelId = messageRepository.findAllByChannelId(channelId);
        cache.putAll(allByChannelId.stream().collect(Collectors.toMap(ConcreteMessage::getId, Function.identity())));
        return allByChannelId;
    }

    @Override
    public ConcreteMessage save(ConcreteMessage message) {
        cache.put(message.getId(), message);
        Map<Long, ConcreteMessage> messages = messageRepository.findAllByChannelId(message.getChannel().getId())
                .stream()
                .collect(Collectors.toMap(ConcreteMessage::getId, Function.identity()));
        cache.putAll(messages);
        return messageRepository.save(message);
    }
}
