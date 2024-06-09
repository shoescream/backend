package com.sideproject.shoescream.notification.controller;

import com.sideproject.shoescream.global.dto.response.Response;
import com.sideproject.shoescream.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping(value = "/subscribe", produces = "text/event-stream")
    public SseEmitter subscribe(@RequestHeader(value = "Last-Event-Id", required = false, defaultValue = "") String lastEventId,
                                          Authentication authentication) {
        return notificationService.subscribe(authentication.getName(), lastEventId);
    }
}
