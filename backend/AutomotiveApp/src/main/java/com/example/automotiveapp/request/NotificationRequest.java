package com.example.automotiveapp.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class NotificationRequest {
    private Long notificationId;
    private Long userTriggeredId;
    private Long receiverId;
    private String content;
    private LocalDateTime createdAt;
    private boolean isRead;
    private String type;
    private Long entityId;
}
