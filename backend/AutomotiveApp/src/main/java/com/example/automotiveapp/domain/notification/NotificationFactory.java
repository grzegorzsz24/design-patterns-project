package com.example.automotiveapp.domain.notification;

import com.example.automotiveapp.domain.User.User;

// L1 Factory - third impl
public abstract class NotificationFactory {

    public Notification create(NotificationType type, User userTriggered, User receiver, Long entityId, String content) {
        Notification notification = createNotification(type, userTriggered, receiver, entityId, getDefaultContentForType(type));
        System.out.println(notification);
        return notification;
    }

    protected abstract Notification createNotification(NotificationType type, User userTriggered, User receiver, Long entityId, String content);

    // L3 Template - second impl
    protected abstract String getDefaultContentForType(NotificationType type);
}
