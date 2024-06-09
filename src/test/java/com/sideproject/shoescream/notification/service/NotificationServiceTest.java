package com.sideproject.shoescream.notification.service;

import com.sideproject.shoescream.member.entity.Member;
import com.sideproject.shoescream.member.repository.MemberRepository;
import com.sideproject.shoescream.notification.constant.NotificationType;
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
        given(memberRepository.findByMemberId(member.getMemberId())).willReturn(Optional.of(member));

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
        given(memberRepository.findByMemberId(member.getMemberId())).willReturn(Optional.of(member));
        given(emitterRepository.save(anyString(), any(SseEmitter.class))).willReturn(sseEmitter);
        given(notificationRepository.save(any(Notification.class))).willReturn(createNotification(member, "결제 알림", "test/url", NotificationType.PAYMENT));

        // When
        notificationService.subscribe(member.getMemberId(), lastEventId);

        // Then
        Assertions.assertDoesNotThrow(() -> notificationService.send(member, "결제 알림", "test/url", NotificationType.PAYMENT));
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

    private Notification createNotification(Member receiver, String content, String relatedUrl, NotificationType notificationType) {
        return Notification.builder()
                .receiver(receiver)
                .content(content)
                .relatedUrl(relatedUrl)
                .notificationType(notificationType)
                .build();
    }
}