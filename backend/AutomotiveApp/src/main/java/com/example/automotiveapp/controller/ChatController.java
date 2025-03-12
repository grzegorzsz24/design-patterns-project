package com.example.automotiveapp.controller;

import com.example.automotiveapp.domain.message.ConcreteMessage;
import com.example.automotiveapp.dto.ChannelDto;
import com.example.automotiveapp.dto.MessageDto;
import com.example.automotiveapp.mapper.MessageDtoMapper;
import com.example.automotiveapp.repository.message.MessageRepository;
import com.example.automotiveapp.request.MessageRequest;
import com.example.automotiveapp.request.adapter.MessageRequestAdapter;
import com.example.automotiveapp.service.ChannelService;
import com.example.automotiveapp.service.MessageService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final ChannelService channelService;
    private final SimpMessagingTemplate messagingTemplate;
    private final MessageDtoMapper messageDtoMapper;
    private final MessageRepository messageRepository;

    // start L1 Singleton - second usage
    private MessageService messageService;

    @PostConstruct
    public void init() {
        this.messageService = MessageService.getInstance(messageRepository);
    }

    @MessageMapping("/chat")
    public void submitMessage(@Payload MessageRequest messageDto) {
        ConcreteMessage message = messageService.saveMessage(messageDtoMapper.map(new MessageRequestAdapter(messageDto)));
        messagingTemplate.convertAndSendToUser(
                String.valueOf(messageDto.getReceiverId()),
                "/queue/messages", MessageDtoMapper.map(message)
        );
    }

//    @GetMapping("/messages/{senderId}/{receiverId}")
//    public ResponseEntity<?> getChatMessages(@PathVariable Long senderId, @PathVariable Long receiverId) {
//        return ResponseEntity.ok(messageService.findMessages(senderId, receiverId));
//    }

    @GetMapping("/user/chats")
    public ResponseEntity<List<ChannelDto>> getUserChats() {
        return ResponseEntity.ok(channelService.findUserChats());
    }

    @GetMapping("/user/chat/messages")
    public ResponseEntity<List<MessageDto>> getChatMessages(@RequestParam Long chatId) {
        return ResponseEntity.ok(messageService.findChatMessages(chatId));
    }
}
