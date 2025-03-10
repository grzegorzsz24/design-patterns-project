package com.example.automotiveapp.controller;

import com.example.automotiveapp.dto.NotificationDto;
import com.example.automotiveapp.dto.UserDto;
import com.example.automotiveapp.service.NotificationService;
import com.example.automotiveapp.service.UserService;
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
    private final NotificationService notificationService;
    private final UserService userService;

    @MessageMapping("/notification")
    public void sendNotification(@Payload NotificationDto notificationRequest) {
        NotificationDto notificationDto = notificationService.saveNotification(notificationRequest);
        simpMessagingTemplate.convertAndSendToUser(
                String.valueOf(notificationRequest.getReceiverId()),
                "/queue/notifications", notificationDto
        );
    }

    @GetMapping("/user/notifications")
    public ResponseEntity<List<NotificationDto>> getUserNotifications() {
        return ResponseEntity.ok(notificationService.findUserNotifications());
    }

    @PostMapping("/user/notification/read")
    public ResponseEntity<NotificationDto> readNotification(@RequestParam Long notificationId) {
        return ResponseEntity.ok(notificationService.setNotificationAsRead(notificationId));
    }

    @MessageMapping("/admin/notification")
    public void sendAdminNotification(@Payload NotificationDto notificationRequest) {
        NotificationDto notificationDto = notificationService.saveNotification(notificationRequest);
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
        NotificationDto notificationDto = notificationService.saveNotification(notificationRequest);
        simpMessagingTemplate.convertAndSendToUser(
                String.valueOf(notificationRequest.getReceiverId()),
                "/queue/article/notifications", notificationDto
        );
    }
}
