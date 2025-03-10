package com.example.automotiveapp.service;

import com.example.automotiveapp.domain.notification.ConcreteNotification;
import com.example.automotiveapp.dto.NotificationDto;
import com.example.automotiveapp.exception.ResourceNotFoundException;
import com.example.automotiveapp.mapper.NotificationDtoMapper;
import com.example.automotiveapp.repository.NotificationRepository;
import com.example.automotiveapp.service.utils.SecurityUtils;

import java.util.List;

public enum NotificationService {

    INSTANCE;

    private NotificationRepository notificationRepository;
    private NotificationDtoMapper notificationDtoMapper;

    public void init(NotificationRepository repo, NotificationDtoMapper mapper) {
        if (this.notificationRepository == null) {
            this.notificationRepository = repo;
        }
        if (this.notificationDtoMapper == null) {
            this.notificationDtoMapper = mapper;
        }
    }

    public NotificationDto saveNotification(NotificationDto notificationDto) {
        ConcreteNotification notification = notificationRepository.save(notificationDtoMapper.map(notificationDto));
        return NotificationDtoMapper.map(notification);
    }

    public List<NotificationDto> findUserNotifications() {
        return notificationRepository.findAllByRecevierEmail(SecurityUtils.getCurrentUserEmail())
                .stream()
                .map(NotificationDtoMapper::map)
                .toList();
    }

    public NotificationDto setNotificationAsRead(Long notificationId) {
        ConcreteNotification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new ResourceNotFoundException("Nie znaleziono powiadomienia"));
        notification.setRead(true);
        notificationRepository.save(notification);
        return NotificationDtoMapper.map(notification);
    }
}
