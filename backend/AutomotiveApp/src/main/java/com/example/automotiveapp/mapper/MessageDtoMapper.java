package com.example.automotiveapp.mapper;

import com.example.automotiveapp.domain.message.Message;
import com.example.automotiveapp.domain.User.User;
import com.example.automotiveapp.domain.message.MessageFactory;
import com.example.automotiveapp.dto.MessageDto;
import com.example.automotiveapp.exception.ResourceNotFoundException;
import com.example.automotiveapp.repository.ChannelRepository;
import com.example.automotiveapp.repository.UserRepository;
import com.example.automotiveapp.service.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class MessageDtoMapper {
    private final UserRepository userRepository;
    private final ChannelService channelService;
    private final ChannelRepository channelRepository;

    public static MessageDto map(Message message) {
        MessageDto messageDto = new MessageDto();
        BeanUtils.copyProperties(message, messageDto);
        messageDto.setSenderId(message.getSender().getId());
        messageDto.setReceiverId(message.getReceiver().getId());
        messageDto.setChannelId(message.getChannel().getId());
        return messageDto;
    }

    public Message map(MessageDto messageDto) {

        User sender = userRepository.findById(messageDto.getSenderId())
                .orElseThrow(() -> new ResourceNotFoundException("Nie znaleziono użytkownika"));
        User receiver = userRepository.findById(messageDto.getReceiverId())
                .orElseThrow(() -> new ResourceNotFoundException("Nie znaleziono użytkownika"));

        Long channelId = channelService.getChannelId(sender.getId(), receiver.getId());
        return MessageFactory.createTextMessage(channelRepository.findById(channelId).get(), sender,
                messageDto.getMessage(), receiver, LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
    }
}
