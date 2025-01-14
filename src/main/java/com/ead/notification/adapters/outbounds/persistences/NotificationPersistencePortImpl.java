package com.ead.notification.adapters.outbounds.persistences;

import com.ead.notification.adapters.outbounds.entities.NotificationEntity;
import com.ead.notification.adapters.outbounds.persistences.repositories.NotificationRepository;
import com.ead.notification.core.domain.NotificationDomain;
import com.ead.notification.core.domain.PageInfo;
import com.ead.notification.core.domain.enums.NotificationStatus;
import com.ead.notification.core.ports.NotificationPersistencePort;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class NotificationPersistencePortImpl implements NotificationPersistencePort {

    private final NotificationRepository notificationRepository;
    private final ModelMapper modelMapper;

    public NotificationPersistencePortImpl(NotificationRepository notificationRepository, ModelMapper modelMapper) {
        this.notificationRepository = notificationRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public NotificationDomain saveNotification(NotificationDomain notificationDomain) {
        var notificationEntity = notificationRepository.save(modelMapper.map(notificationDomain, NotificationEntity.class));
        return modelMapper.map(notificationEntity, NotificationDomain.class);
    }

    @Override
    public List<NotificationDomain> findAllNotificatonsByUser(UUID userId, PageInfo pageInfo) {
        return List.of();
    }

    @Override
    public Optional<NotificationDomain> findByNotificationIdAndUserId(UUID notificationId, UUID userId) {
        return Optional.empty();
    }

    @Override
    public NotificationDomain updateNotification(NotificationStatus notificationRecordDto, NotificationDomain notificationModel) {
        return null;
    }
}
