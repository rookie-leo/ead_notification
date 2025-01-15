package com.ead.notification.adapters.dtos;

import java.util.UUID;

public record NotificationRecordCommandDto(
        String title,
        String message,
        UUID userId
) {
}
