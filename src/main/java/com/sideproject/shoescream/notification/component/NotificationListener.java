package com.sideproject.shoescream.notification.component;

import com.sideproject.shoescream.notification.dto.request.NotificationRequest;
import com.sideproject.shoescream.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class NotificationListener {

    private final NotificationService notificationService;

    @TransactionalEventListener
    @Async
    public void handleNotification(NotificationRequest notificationRequest) {
        notificationService.send(notificationRequest);
    }
}
