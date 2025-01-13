package com.ead.notification.adapters.dtos;

import com.ead.notification.enums.NotificationStatus;
import jakarta.validation.constraints.NotNull;

public record NotificationRecordDto(
        @NotNull NotificationStatus notificationStatus
        ) {
}
