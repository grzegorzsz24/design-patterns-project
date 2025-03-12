package com.example.automotiveapp.repository.notification;

import com.example.automotiveapp.domain.notification.ConcreteNotification;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

// L2 Proxy - third impl
@AllArgsConstructor
public class NotificationCacheProxy implements NotificationRepository {

    private final NotificationRepository notificationRepository;
    private final HashMap<Long, ConcreteNotification> cache = new HashMap<>();

    @Override
    public List<ConcreteNotification> findAllByRecevierEmail(String email) {
        List<ConcreteNotification> concreteNotification = cache.values().stream()
                .filter(notification -> notification.getRecevier().getEmail().equals(email))
                .toList();

        if (!concreteNotification.isEmpty()) {
            return concreteNotification;
        }
        List<ConcreteNotification> allByReceiverEmail = notificationRepository.findAllByRecevierEmail(email);
        cache.putAll(allByReceiverEmail.stream().collect(Collectors.toMap(ConcreteNotification::getId, Function.identity())));
        return allByReceiverEmail;
    }

    @Override
    public ConcreteNotification save(ConcreteNotification notification) {
        cache.entrySet().removeIf(entry -> entry.getValue().getRecevier().getEmail().equals(notification.getRecevier().getEmail()));
        cache.put(notification.getRecevier().getId(), notification);
        return notificationRepository.save(notification);
    }

    @Override
    public Optional<ConcreteNotification> findById(Long notificationId) {
        if (cache.containsKey(notificationId)) {
            return Optional.of(cache.get(notificationId));
        }
        Optional<ConcreteNotification> concreteNotification = notificationRepository.findById(notificationId);
        concreteNotification.ifPresent(notification -> cache.put(notificationId, notification));
        return concreteNotification;
    }
}
