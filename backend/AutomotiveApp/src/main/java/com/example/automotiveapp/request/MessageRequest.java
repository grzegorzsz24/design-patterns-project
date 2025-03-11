package com.example.automotiveapp.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MessageRequest {
    private Long senderId;
    private Long receiverId;
    @NotBlank
    private String message;
    private LocalDateTime createdAt;
    private Long channelId;
}
