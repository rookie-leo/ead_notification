package com.ead.notification.service.Impl;

import com.ead.notification.adapters.dtos.NotificationRecordCommandDto;
import com.ead.notification.adapters.dtos.NotificationRecordDto;
import com.ead.notification.core.domain.enums.NotificationStatus;
import com.ead.notification.adapters.exceptions.NotFoundException;
import com.ead.notification.models.NotificationModel;
import com.ead.notification.repositories.NotificationRepository;
import com.ead.notification.core.ports.NotificationServicePort;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@Service
public class NotificationServiceImpl implements NotificationServicePort {

    final NotificationRepository notificationRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public NotificationModel saveNotification(NotificationRecordCommandDto notificationRecordCommandDto) {
        var notificationModel = new NotificationModel();
        BeanUtils.copyProperties(notificationRecordCommandDto, notificationModel);
        notificationModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        notificationModel.setNotificationStatus(NotificationStatus.CREATED);

        return notificationRepository.save(notificationModel);
    }

    @Override
    public Page<NotificationModel> findAllNotificatonsByUser(UUID userId, Pageable pageable) {
        return notificationRepository.findAllByUserIdAndNotificationStatus(userId, NotificationStatus.CREATED, pageable);
    }

    @Override
    public Optional<NotificationModel> findByNotificationIdAndUserId(UUID notificationId, UUID userId) {
        Optional<NotificationModel> optionalNotificationModel =
                notificationRepository.findByNotificationIdAndUserId(notificationId, userId);
        if (optionalNotificationModel.isEmpty()) throw new NotFoundException("Notification for this user not found!");

        return optionalNotificationModel;
    }

    @Override
    public NotificationModel updateNotification(NotificationRecordDto notificationRecordDto, NotificationModel notificationModel) {
        notificationModel.setNotificationStatus(notificationRecordDto.notificationStatus());
        return notificationRepository.save(notificationModel);
    }
}
