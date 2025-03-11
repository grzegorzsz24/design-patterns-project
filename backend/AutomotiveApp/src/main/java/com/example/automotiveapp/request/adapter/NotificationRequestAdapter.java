package com.example.automotiveapp.request.adapter;

import com.example.automotiveapp.dto.NotificationDto;
import com.example.automotiveapp.request.NotificationRequest;
import lombok.AllArgsConstructor;

// L2 Adapter - third impl
@AllArgsConstructor
public class NotificationRequestAdapter extends NotificationDto {

    private final NotificationRequest notificationRequest;

    @Override
    public Long getUserTriggeredId() {
        return notificationRequest.getUserTriggeredId();
    }

    @Override
    public Long getReceiverId() {
        return notificationRequest.getReceiverId();
    }

    @Override
    public String getType() {
        return notificationRequest.getType();
    }

    @Override
    public Long getEntityId() {
        return notificationRequest.getEntityId();
    }

    @Override
    public String getContent() {
        return notificationRequest.getContent();
    }
}
