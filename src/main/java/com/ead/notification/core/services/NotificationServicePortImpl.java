package com.ead.notification.core.services;

import com.ead.notification.core.domain.NotificationDomain;
import com.ead.notification.core.domain.PageInfo;
import com.ead.notification.core.domain.enums.NotificationStatus;
import com.ead.notification.core.ports.NotificationPersistencePort;
import com.ead.notification.core.ports.NotificationServicePort;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class NotificationServicePortImpl implements NotificationServicePort {

    private final NotificationPersistencePort notificationPersistencePort;

    public NotificationServicePortImpl(NotificationPersistencePort notificationPersistencePort) {
        this.notificationPersistencePort = notificationPersistencePort;
    }

    @Override
    public NotificationDomain saveNotification(NotificationDomain notificationDomain) {
        notificationDomain.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        notificationDomain.setNotificationStatus(NotificationStatus.CREATED);

        return notificationPersistencePort.saveNotification(notificationDomain);
    }

    @Override
    public List<NotificationDomain> findAllNotificatonsByUser(UUID userId, PageInfo pageInfo) {
        return notificationPersistencePort.findAllByUserIdAndNotificationStatus(userId, NotificationStatus.CREATED, pageInfo);
    }

    @Override
    public Optional<NotificationDomain> findByNotificationIdAndUserId(UUID notificationId, UUID userId) {
        return notificationPersistencePort.findByNotificationIdAndUserId(notificationId, userId);
    }

    @Override
    public NotificationDomain updateNotification(NotificationStatus notificationStatus, NotificationDomain notificationDomain) {
        return notificationPersistencePort.updateNotification(notificationStatus, notificationDomain);
    }
}
