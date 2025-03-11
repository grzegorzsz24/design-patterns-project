package com.example.automotiveapp.service;

import com.example.automotiveapp.domain.message.ConcreteMessage;
import com.example.automotiveapp.dto.MessageDto;
import com.example.automotiveapp.mapper.MessageDtoMapper;
import com.example.automotiveapp.repository.MessageRepository;

import java.util.List;

// L1 Singleton - second impl
public class MessageService {

    private static volatile MessageService instance;

    private final MessageRepository messageRepository;

    private MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public ConcreteMessage saveMessage(ConcreteMessage message) {
        return messageRepository.save(message);
    }

    public static MessageService getInstance(MessageRepository messageRepository) {
        if (instance == null) {
            synchronized (MessageService.class) {
                if (instance == null) {
                    instance = new MessageService(messageRepository);
                }
            }
        }
        return instance;
    }

//    public List<Message> findMessages(Long senderId, Long receiverId) {
//        Long channelId = channelService.getChannelId(senderId, receiverId);
//        List<Message> messages = messageRepository.findAllByChannelId(channelId);
//        if (messages.isEmpty()) {
//            messages = new ArrayList<>();
//        }
//        return messages;
//    }

    public List<MessageDto> findChatMessages(Long chatId) {
        return messageRepository.findAllByChannelId(chatId)
                .stream()
                .map(MessageDtoMapper::map)
                .toList();
    }
}
