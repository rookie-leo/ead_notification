package com.ead.notification.controllers;

import com.ead.notification.dtos.NotificationRecordDto;
import com.ead.notification.models.NotificationModel;
import com.ead.notification.service.NotificationService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class UserNotificationController {

    final NotificationService notificationService;

    public UserNotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/users/{userId}/notifications")
    public ResponseEntity<Page<NotificationModel>> getAllNotificationByUser(
            @PathVariable(value = "userId") UUID userId,
            Pageable pageable
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(notificationService.findAllNotificatonsByUser(userId, pageable));
    }

    @PutMapping("/users/{userId}/notifications/{notificationId}")
    public ResponseEntity<Object> updateNotification(
            @PathVariable(value = "userId") UUID userId,
            @PathVariable(value = "notificationId") UUID notificationId,
            @RequestBody @Valid NotificationRecordDto notificationRecordDto
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        notificationService.updateNotification(
                                notificationRecordDto,
                                notificationService.findByNotificationIdAndUserId(notificationId, userId).get()
                        )
                );
    }
}
