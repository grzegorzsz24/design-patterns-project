package com.example.automotiveapp.controller;

import com.example.automotiveapp.dto.NotificationDto;
import com.example.automotiveapp.dto.UserDto;
import com.example.automotiveapp.mapper.NotificationDtoMapper;
import com.example.automotiveapp.repository.notification.NotificationRepository;
import com.example.automotiveapp.request.NotificationRequest;
import com.example.automotiveapp.request.adapter.NotificationRequestAdapter;
import com.example.automotiveapp.service.NotificationService;
import com.example.automotiveapp.service.UserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class NotificationController {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final UserService userService;
    private final NotificationRepository notificationRepository;
    private final NotificationDtoMapper notificationDtoMapper;

    // start L1 Singleton - third usage
    @PostConstruct
    public void init() {
        NotificationService.INSTANCE.init(notificationRepository, notificationDtoMapper);
    }

    @MessageMapping("/notification")
    public void sendNotification(@Payload NotificationRequest notificationRequest) {
        NotificationDto notificationDto = NotificationService.INSTANCE.saveNotification(new NotificationRequestAdapter(notificationRequest));
        simpMessagingTemplate.convertAndSendToUser(
                String.valueOf(notificationRequest.getReceiverId()),
                "/queue/notifications", notificationDto
        );
    }

    @GetMapping("/user/notifications")
    public ResponseEntity<List<NotificationDto>> getUserNotifications() {
        return ResponseEntity.ok(NotificationService.INSTANCE.findUserNotifications());
    }

    @PostMapping("/user/notification/read")
    public ResponseEntity<NotificationDto> readNotification(@RequestParam Long notificationId) {
        return ResponseEntity.ok(NotificationService.INSTANCE.setNotificationAsRead(notificationId));
    }

    @MessageMapping("/admin/notification")
    public void sendAdminNotification(@Payload NotificationDto notificationRequest) {
        NotificationDto notificationDto = NotificationService.INSTANCE.saveNotification(notificationRequest);
        List<UserDto> adminUsers = userService.getUsersWithAdminRole();
        for (UserDto adminUser : adminUsers) {
            simpMessagingTemplate.convertAndSendToUser(
                    String.valueOf(adminUser.getId()),
                    "/queue/admin/notifications", notificationDto
            );
        }
    }

    @MessageMapping("/article/notification")
    public void sendArticleApprovedStatusNotification(@Payload NotificationDto notificationRequest) {
        NotificationDto notificationDto = NotificationService.INSTANCE.saveNotification(notificationRequest);
        simpMessagingTemplate.convertAndSendToUser(
                String.valueOf(notificationRequest.getReceiverId()),
                "/queue/article/notifications", notificationDto
        );
    }
}
