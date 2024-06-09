package com.sideproject.shoescream.notification.service;

import com.sideproject.shoescream.member.entity.Member;
import com.sideproject.shoescream.member.repository.MemberRepository;
import com.sideproject.shoescream.notification.dto.response.NotificationResponse;
import com.sideproject.shoescream.notification.constant.NotificationType;
import com.sideproject.shoescream.notification.entity.Notification;
import com.sideproject.shoescream.notification.repository.EmitterRepository;
import com.sideproject.shoescream.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final EmitterRepository emitterRepository;
    private final NotificationRepository notificationRepository;
    private final MemberRepository memberRepository;

    private static final Long SSE_TIME_OUT = 1800000L;

    public SseEmitter subscribe(String memberId, String lastEventId) {
        Member receiver = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new RuntimeException());
        String emitterId = makeTimeIncludeId(receiver.getMemberNumber());
        SseEmitter emitter = emitterRepository.save(emitterId, new SseEmitter(SSE_TIME_OUT));
        emitter.onCompletion(() -> emitterRepository.deleteById(emitterId));
        emitter.onTimeout(() -> emitterRepository.deleteById(emitterId));

        String eventId = makeTimeIncludeId(receiver.getMemberNumber());
        sendNotification(emitter, eventId, emitterId, "EventStream Created. [userId=" + receiver.getMemberNumber() + "]");

        // 클라이언트가 미수신한 Event 목록이 존재할 경우 전송하여 Event 유실을 예방
        if (hasLostData(lastEventId)) {
            sendLostData(lastEventId, receiver.getMemberNumber(), emitterId, emitter);
        }

        return emitter;
    }

    private String makeTimeIncludeId(Long memberNumber) {
        return memberNumber + "_" + System.currentTimeMillis();
    }

    private void sendNotification(SseEmitter emitter, String eventId, String emitterId, Object data) {
        try {
            emitter.send(SseEmitter.event()
                    .id(eventId)
                    .data(data));
        } catch (IOException e) {
            emitterRepository.deleteById(emitterId);
        }
    }

    private boolean hasLostData(String lastEventId) {
        return !lastEventId.isEmpty();
    }

    private void sendLostData(String lastEventId, Long memberNumber, String emitterId, SseEmitter emitter) {
        Map<String, Object> eventCaches = emitterRepository.findAllEventCacheStartWithByMemberNumber(String.valueOf(memberNumber));
        eventCaches.entrySet().stream()
                .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
                .forEach(entry -> sendNotification(emitter, entry.getKey(), emitterId, entry.getValue()));
    }

    public void send(Member receiver, String content, String relatedUrl, NotificationType notificationType) {
        Notification notification = notificationRepository.save(createNotification(receiver, content, relatedUrl, notificationType));

        String receiverNumber = String.valueOf(receiver.getMemberNumber());
        String eventId = receiverNumber + "_" + System.currentTimeMillis();
        Map<String, SseEmitter> emitters = emitterRepository.findAllEmitterStartWithByMemberNumber(receiverNumber);
        emitters.forEach(
                (key, emitter) -> {
                    emitterRepository.saveEventCache(key, notification);
                    sendNotification(emitter, eventId, key, NotificationResponse.createNotificationResponse(notification));
                }
        );
    }

    private Notification createNotification(Member receiver, String content, String relatedUrl, NotificationType notificationType) {
        return Notification.builder()
                .receiver(receiver)
                .content(content)
                .relatedUrl(relatedUrl)
                .notificationType(notificationType)
                .build();
    }
}
