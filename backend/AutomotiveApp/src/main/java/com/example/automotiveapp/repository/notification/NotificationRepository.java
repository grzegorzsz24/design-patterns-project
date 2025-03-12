package com.example.automotiveapp.repository.notification;

import com.example.automotiveapp.domain.notification.ConcreteNotification;

import java.util.List;
import java.util.Optional;


public interface NotificationRepository {
    List<ConcreteNotification> findAllByRecevierEmail(String email);

    ConcreteNotification save(ConcreteNotification map);

    Optional<ConcreteNotification> findById(Long notificationId);
}
