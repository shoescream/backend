package com.sideproject.shoescream.member.dto.response;

import com.sideproject.shoescream.notification.constant.NotificationType;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record MyNotificationResponse(
        long notificationNumber,
        String notificationContent,
        NotificationType notificationType,
        String buyerId,
        LocalDateTime createdAt,
        boolean isRead,
        Object object
) {
}
