package com.sideproject.shoescream.notification.service;

import com.sideproject.shoescream.member.entity.Member;
import com.sideproject.shoescream.member.repository.MemberRepository;
import com.sideproject.shoescream.notification.constant.NotificationType;
import com.sideproject.shoescream.notification.dto.request.NotificationRequest;
import com.sideproject.shoescream.notification.entity.Notification;
import com.sideproject.shoescream.notification.repository.EmitterRepository;
import com.sideproject.shoescream.notification.repository.NotificationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Optional;

import static org.mockito.BDDMockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @InjectMocks
    NotificationService notificationService;

    @Mock
    NotificationRepository notificationRepository;

    @Mock
    EmitterRepository emitterRepository;

    @Mock
    MemberRepository memberRepository;

    @Test
    @DisplayName("알림 구독을 진행")
    void subscribe() throws Exception {
        // Given
        Member member = createMember("wnsdhqo");

        SseEmitter sseEmitter = new SseEmitter();
        given(emitterRepository.save(anyString(), any(SseEmitter.class))).willReturn(sseEmitter);

        // When
        String lastEventId = "";
        SseEmitter result = notificationService.subscribe(member.getMemberId(), lastEventId);

        // Then
        assertNotNull(result);
    }

    @Test
    @DisplayName("알림 메세지를 전송한다.")
    void send() throws Exception {
        // Given
        Member member = createMember("wnsdhqo");
        SseEmitter sseEmitter = new SseEmitter();
        String lastEventId = "";
        given(emitterRepository.save(anyString(), any(SseEmitter.class))).willReturn(sseEmitter);
        given(notificationRepository.save(any(Notification.class))).willReturn(createNotification(1L, member, "gd", NotificationType.PAYMENT));

        // When
        notificationService.subscribe(member.getMemberId(), lastEventId);

        // Then
        Assertions.assertDoesNotThrow(() -> notificationService.send(createNotificationRequest(member)));
    }

    private Member createMember(String memberId) {
        return Member.builder()
                .memberId(memberId)
                .password("Gkrehd102!")
                .email("wnsdhqo@naver.com")
                .name("배준오")
                .profileImage(null)
                .build();
    }

    private Notification createNotification(long domainNumber, Member receiver, String content, NotificationType notificationType) {
        return Notification.builder()
                .domainNumber(domainNumber)
                .receiver(receiver)
                .notificationContent(content)
                .notificationType(notificationType)
                .build();
    }

    private NotificationRequest createNotificationRequest(Member receiver) {
        return NotificationRequest.builder()
                .domainNumber(1L)
                .receiver(receiver)
                .notificationType(NotificationType.PAYMENT)
                .content("하이")
                .object(new Object())
                .build();

    }
}