package com.example.automotiveapp.mapper;

import com.example.automotiveapp.domain.User.User;
import com.example.automotiveapp.domain.notification.ConcreteNotification;
import com.example.automotiveapp.domain.notification.NotificationFactory;
import com.example.automotiveapp.domain.notification.NotificationType;
import com.example.automotiveapp.dto.NotificationDto;
import com.example.automotiveapp.exception.ResourceNotFoundException;
import com.example.automotiveapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationDtoMapper {
    private final UserRepository userRepository;
    private final NotificationFactory notificationFactory;

    public static NotificationDto map(ConcreteNotification notification) {
        NotificationDto notificationDto = new NotificationDto();
        BeanUtils.copyProperties(notification, notificationDto);
        notificationDto.setUserTriggeredId(notification.getUserTriggered().getId());
        notificationDto.setReceiverId(notification.getRecevier().getId());
        notificationDto.setType(String.valueOf(notification.getType()));
        notificationDto.setNotificationId(notification.getId());
        return notificationDto;
    }

    public ConcreteNotification map(NotificationDto notificationDto) {
        User userTriggered = userRepository.findById(notificationDto.getUserTriggeredId())
                .orElseThrow(() -> new ResourceNotFoundException("Nie znaleziono użytkownika"));
        User receiver = userRepository.findById(notificationDto.getReceiverId())
                .orElseThrow(() -> new ResourceNotFoundException("Nie znaleziono użytkownika"));
        NotificationType notificationType = NotificationType.valueOf(notificationDto.getType());
        Long entityId = notificationDto.getEntityId();
        String content = notificationDto.getContent();

        // start L1 Factory - third usage
        return (ConcreteNotification) notificationFactory.create(notificationType, userTriggered, receiver, entityId, content);
    }
}
