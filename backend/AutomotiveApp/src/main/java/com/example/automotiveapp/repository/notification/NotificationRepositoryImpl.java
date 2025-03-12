package com.example.automotiveapp.repository.notification;

import com.example.automotiveapp.domain.notification.ConcreteNotification;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class NotificationRepositoryImpl implements NotificationRepository {

    private final NotificationJpaRepository notificationJpaRepository;

    @Override
    public List<ConcreteNotification> findAllByRecevierEmail(String email) {
        return notificationJpaRepository.findAllByRecevierEmail(email);
    }

    @Override
    public ConcreteNotification save(ConcreteNotification notification) {
        return notificationJpaRepository.save(notification);
    }

    @Override
    public Optional<ConcreteNotification> findById(Long notificationId) {
        return notificationJpaRepository.findById(notificationId);
    }
}
