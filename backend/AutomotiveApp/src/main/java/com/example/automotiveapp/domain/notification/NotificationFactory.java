package com.example.automotiveapp.domain.notification;

import com.example.automotiveapp.domain.User.User;

public abstract class NotificationFactory {

    public Notification create(NotificationType type, User userTriggered, User receiver, Long entityId, String content) {
        Notification notification = createNotification(type, userTriggered, receiver, entityId, getDefaultContentForType(type));
        System.out.println(notification);
        return notification;
    }

    protected abstract Notification createNotification(NotificationType type, User userTriggered, User receiver, Long entityId, String content);

    protected abstract String getDefaultContentForType(NotificationType type);
}
