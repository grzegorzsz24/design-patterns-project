package com.example.automotiveapp.domain.notification;

import com.example.automotiveapp.domain.User.User;

public abstract class NotificationFactory {
    public Notification create(NotificationType type, User userTriggered, User receiver, Long entityId, String content) {
        return createNotification(type, userTriggered, receiver, entityId, getDefaultContentForType(type));
    }

    protected abstract Notification createNotification(NotificationType type, User userTriggered, User receiver, Long entityId, String content);

    protected abstract String getDefaultContentForType(NotificationType type);
}
