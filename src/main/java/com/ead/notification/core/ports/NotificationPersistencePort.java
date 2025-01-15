package com.ead.notification.core.ports;

import com.ead.notification.core.domain.NotificationDomain;
import com.ead.notification.core.domain.PageInfo;
import com.ead.notification.core.domain.enums.NotificationStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NotificationPersistencePort {
    NotificationDomain saveNotification(NotificationDomain notificationDomain);

    List<NotificationDomain> findAllNotificatonsByUser(UUID userId, PageInfo pageInfo);

    Optional<NotificationDomain> findByNotificationIdAndUserId(UUID notificationId, UUID userId);

    NotificationDomain updateNotification(NotificationStatus notificationRecordDto, NotificationDomain notificationModel);

    List<NotificationDomain> findAllByUserIdAndNotificationStatus(UUID userId, NotificationStatus notificationStatus, PageInfo pageInfo);
}
