package com.example.automotiveapp.domain.notification;

import com.example.automotiveapp.domain.User.User;

import java.time.LocalDateTime;

public class NotificationFactory {

    private NotificationFactory() {
    }

    public static Notification createNotification(NotificationType type, User userTriggered, User receiver, Long entityId, String content) {
        Notification notification = new Notification();
        notification.setType(type);
        notification.setUserTriggered(userTriggered);
        notification.setRecevier(receiver);
        notification.setEntityId(entityId);
        notification.setContent(getDefaultContentForType(type));
        notification.setCreatedAt(LocalDateTime.now());
        notification.setRead(false);
        return notification;
    }

    private static String getDefaultContentForType(NotificationType type) {
        return switch (type) {
            case POST_COMMENT -> "Masz nowy komentarz do posta.";
            case POST_LIKE -> "Post został polubiony.";
            case ARTICLE_LIKE -> "Artykuł został polubiony.";
            case FORUM_COMMENT -> "Nowy komentarz w forum.";
            case INVITATION_SENT -> "Wysłano zaproszenie.";
            case INVITATION_ACCEPTED -> "Zaproszenie zostało zaakceptowane.";
            case POST_REPORT -> "Post został zgłoszony.";
            case FORUM_REPORT -> "Forum zostało zgłoszone.";
            case EVENT_REPORT -> "Wydarzenie zostało zgłoszone.";
            case ARTICLE_APPROVED -> "Artykuł został zatwierdzony.";
            case ARTICLE_DELETED -> "Artykuł został usunięty.";
            default -> "Masz nowe powiadomienie.";
        };
    }
}
