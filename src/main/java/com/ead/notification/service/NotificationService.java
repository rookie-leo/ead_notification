package com.ead.notification.service;

import com.ead.notification.adapters.dtos.NotificationRecordCommandDto;
import com.ead.notification.adapters.dtos.NotificationRecordDto;
import com.ead.notification.models.NotificationModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface NotificationService {
    NotificationModel saveNotification(NotificationRecordCommandDto notificationRecordCommandDto);

    Page<NotificationModel> findAllNotificatonsByUser(UUID userId, Pageable pageable);

    Optional<NotificationModel> findByNotificationIdAndUserId(UUID notificationId, UUID userId);

    NotificationModel updateNotification(NotificationRecordDto notificationRecordDto, NotificationModel notificationModel);
}
