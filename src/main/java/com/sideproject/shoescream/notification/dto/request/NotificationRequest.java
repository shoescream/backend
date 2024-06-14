package com.sideproject.shoescream.notification.dto.request;

import com.sideproject.shoescream.member.entity.Member;
import com.sideproject.shoescream.notification.constant.NotificationType;
import lombok.Builder;

@Builder
public record NotificationRequest(
        long domainNumber,
        Member receiver,
        NotificationType notificationType,
        String content,
        Object object
) {
}
