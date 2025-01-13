package com.ead.notification.controllers;

import com.ead.notification.adapters.configs.security.AuthenticationCurrentUserService;
import com.ead.notification.adapters.configs.security.UserDetailsImpl;
import com.ead.notification.adapters.dtos.NotificationRecordDto;
import com.ead.notification.models.NotificationModel;
import com.ead.notification.core.ports.NotificationServicePort;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class UserNotificationController {

    final NotificationServicePort notificationService;
    final AuthenticationCurrentUserService authenticationCurrentUserService;

    public UserNotificationController(NotificationServicePort notificationService, AuthenticationCurrentUserService authenticationCurrentUserService) {
        this.notificationService = notificationService;
        this.authenticationCurrentUserService = authenticationCurrentUserService;
    }

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("/users/{userId}/notifications")
    public ResponseEntity<Page<NotificationModel>> getAllNotificationByUser(
            @PathVariable(value = "userId") UUID userId,
            Pageable pageable
    ) {
        UserDetailsImpl userDetails = authenticationCurrentUserService.getCurrentUser();

        if (userDetails.getUserId().equals(userId) || userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(notificationService.findAllNotificatonsByUser(userId, pageable));
        } else {
            throw new AccessDeniedException("Forbidden");
        }
    }

    @PreAuthorize("hasAnyRole('USER')")
    @PutMapping("/users/{userId}/notifications/{notificationId}")
    public ResponseEntity<Object> updateNotification(
            @PathVariable(value = "userId") UUID userId,
            @PathVariable(value = "notificationId") UUID notificationId,
            @RequestBody @Valid NotificationRecordDto notificationRecordDto
    ) {
        UserDetailsImpl userDetails = authenticationCurrentUserService.getCurrentUser();

        if (userDetails.getUserId().equals(userId) || userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(
                            notificationService.updateNotification(
                                    notificationRecordDto,
                                    notificationService.findByNotificationIdAndUserId(notificationId, userId).get()
                            )
                    );
        } else {
            throw new AccessDeniedException("Forbidden");
        }
    }
}
