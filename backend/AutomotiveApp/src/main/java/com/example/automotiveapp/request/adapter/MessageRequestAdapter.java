package com.example.automotiveapp.request.adapter;

import com.example.automotiveapp.dto.MessageDto;
import com.example.automotiveapp.request.MessageRequest;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

// L2 Adapter - second impl
@AllArgsConstructor
public class MessageRequestAdapter extends MessageDto {
    private final MessageRequest messageRequest;

    public Long getSenderId() {
        return messageRequest.getSenderId();
    }

    public Long getReceiverId() {
        return messageRequest.getReceiverId();
    }

    public String getMessage() {
        return messageRequest.getMessage();
    }

    public LocalDateTime getCreatedAt() {
        return messageRequest.getCreatedAt();
    }

    public Long getChannelId() {
        return messageRequest.getChannelId();
    }
}
