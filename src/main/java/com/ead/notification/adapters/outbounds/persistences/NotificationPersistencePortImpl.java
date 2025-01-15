package com.ead.notification.adapters.outbounds.persistences;

import com.ead.notification.adapters.exceptions.NotFoundException;
import com.ead.notification.adapters.outbounds.entities.NotificationEntity;
import com.ead.notification.adapters.outbounds.persistences.repositories.NotificationRepository;
import com.ead.notification.core.domain.NotificationDomain;
import com.ead.notification.core.domain.PageInfo;
import com.ead.notification.core.domain.enums.NotificationStatus;
import com.ead.notification.core.ports.NotificationPersistencePort;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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
        Optional<NotificationEntity> notificationEntityOptional = notificationRepository.findByNotificationIdAndUserId(notificationId, userId);

        if (notificationEntityOptional.isEmpty()) {
            throw new NotFoundException("Notification for this user not found!");
        }

        return Optional.of(modelMapper.map(notificationEntityOptional.get(), NotificationDomain.class));
    }

    @Override
    public List<NotificationDomain> findAllByUserIdAndNotificationStatus(UUID userId, NotificationStatus notificationStatus, PageInfo pageInfo) {
       var pageable = PageRequest.of(pageInfo.getPageNumber(), pageInfo.getPageSize());

        return notificationRepository.findAllByUserIdAndNotificationStatus(userId, notificationStatus, pageable)
                .stream()
                .map(entity -> modelMapper.map(entity, NotificationDomain.class))
                .collect(Collectors.toList());
    }

    @Override
    public NotificationDomain updateNotification(NotificationStatus notificationStatus, NotificationDomain notificationDomain) {
        notificationDomain.setNotificationStatus(notificationStatus);

        return modelMapper.map(
                notificationRepository.save(modelMapper.map(notificationDomain, NotificationEntity.class)),
                NotificationDomain.class
        );
    }
}
