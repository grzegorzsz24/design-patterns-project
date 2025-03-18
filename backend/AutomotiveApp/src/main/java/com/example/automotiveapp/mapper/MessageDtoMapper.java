package com.example.automotiveapp.mapper;

import com.example.automotiveapp.domain.User.User;
import com.example.automotiveapp.domain.message.ConcreteMessage;
import com.example.automotiveapp.domain.message.MessageFactory;
import com.example.automotiveapp.dto.MessageDto;
import com.example.automotiveapp.exception.ResourceNotFoundException;
import com.example.automotiveapp.repository.ChannelRepository;
import com.example.automotiveapp.repository.UserRepository;
import com.example.automotiveapp.service.channel.ChannelServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class MessageDtoMapper {
    private final UserRepository userRepository;
    private final ChannelServiceImpl channelServiceImpl;
    private final ChannelRepository channelRepository;
    private final MessageFactory messageFactory;

    public static MessageDto map(ConcreteMessage message) {
        MessageDto messageDto = new MessageDto();
        BeanUtils.copyProperties(message, messageDto);
        messageDto.setSenderId(message.getSender().getId());
        messageDto.setReceiverId(message.getReceiver().getId());
        messageDto.setChannelId(message.getChannel().getId());
        return messageDto;
    }

    public ConcreteMessage map(MessageDto messageDto) {

        User sender = userRepository.findById(messageDto.getSenderId())
                .orElseThrow(() -> new ResourceNotFoundException("Nie znaleziono użytkownika"));
        User receiver = userRepository.findById(messageDto.getReceiverId())
                .orElseThrow(() -> new ResourceNotFoundException("Nie znaleziono użytkownika"));

        Long channelId = channelServiceImpl.getChannelId(sender.getId(), receiver.getId());

        // start L1 Factory - second usage
        return (ConcreteMessage) messageFactory.create(channelRepository.findById(channelId).get(), sender,
                messageDto.getMessage(), receiver, LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
    }
}
