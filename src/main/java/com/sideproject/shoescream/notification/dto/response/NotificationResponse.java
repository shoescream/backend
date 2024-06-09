package com.sideproject.shoescream.notification.dto.response;

import com.sideproject.shoescream.notification.entity.Notification;
import lombok.Builder;

@Builder
public record NotificationResponse(
        Long id,
        String receiver,
        String content,
        String relatedUrl,
        String type
) {

    public static NotificationResponse createNotificationResponse(Notification notification) {
        return NotificationResponse.builder()
                .id(notification.getNotificationNumber())
                .receiver(notification.getReceiver().getMemberId())
                .content(notification.getContent())
                .relatedUrl("/test/url")
                .type(notification.getNotificationType().toString())
                .build();
    }
}
