package com.sideproject.shoescream.notification.dto.response;

import com.sideproject.shoescream.notification.dto.request.NotificationRequest;
import com.sideproject.shoescream.notification.entity.Notification;
import lombok.Builder;

@Builder
public record NotificationResponse(
        long notificationNumber,
        String receiver,
        String content,
        String relatedUrl,
        String notificationType,
        Object object
) {

    public static NotificationResponse createNotificationResponse(Notification notification, NotificationRequest notificationRequest) {
        return NotificationResponse.builder()
                .notificationNumber(notification.getNotificationNumber())
                .receiver(notification.getReceiver().getMemberId())
                .content(notification.getNotificationContent())
                .relatedUrl("/test/url")
                .notificationType(notification.getNotificationType().toString())
                .object(notificationRequest.object())
                .build();
    }
}
