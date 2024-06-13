package com.sideproject.shoescream.notification.repository;

import com.sideproject.shoescream.member.entity.Member;
import com.sideproject.shoescream.notification.constant.NotificationType;
import com.sideproject.shoescream.notification.entity.Notification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class EmitterRepositoryImplTest {

    private EmitterRepository emitterRepository = new EmitterRepositoryImpl();
    private Long DEFAULT_TIMEOUT = 60L * 1000L * 60L;

    @Test
    @DisplayName("새로운 Emitter를 추가한다.")
    void save() throws Exception {
        // Given
        Long memberNumber = 1L;
        String emitterId = memberNumber + "_" + System.currentTimeMillis();
        SseEmitter sseEmitter = new SseEmitter(DEFAULT_TIMEOUT);

        // When, Then
        Assertions.assertDoesNotThrow(() -> emitterRepository.save(emitterId, sseEmitter));
    }

    @Test
    @DisplayName("수신한 이벤트를 캐시에 저장한다.")
    void saveEventCache() throws Exception {
        // Given
        Long memberNumber = 1L;
        String eventCacheId = memberNumber + "_" + System.currentTimeMillis();
        Notification notification = createNotification("거래가 성사 되었습니다.", "test/url");

        // When, Then
        Assertions.assertDoesNotThrow(() -> emitterRepository.saveEventCache(eventCacheId, notification));
    }

    @Test
    @DisplayName("어떤 회원이 접속한 모든 Emitter를 찾는다.")
    void findAllEmitterStartWithByMemberNumber() throws Exception {
        // Given
        Long memberNumber = 1L;
        String emitterId1 = memberNumber + "_" + System.currentTimeMillis();
        emitterRepository.save(emitterId1, new SseEmitter(DEFAULT_TIMEOUT));

        Thread.sleep(100);
        String emitterId2 = memberNumber + "_" + System.currentTimeMillis();
        emitterRepository.save(emitterId2, new SseEmitter(DEFAULT_TIMEOUT));

        Thread.sleep(100);
        String emitterId3 = memberNumber + "_" + System.currentTimeMillis();
        emitterRepository.save(emitterId3, new SseEmitter(DEFAULT_TIMEOUT));

        // When
        Map<String, SseEmitter> result = emitterRepository.findAllEmitterStartWithByMemberId(String.valueOf(memberNumber));

        // Then
        Assertions.assertEquals(3, result.size());
    }

    @Test
    @DisplayName("어떤 회원에게 수신된 이벤트를 캐시에서 모두 찾는다.")
    void findAllEventCacheStartWithByMemberNumber() throws Exception {
        // Given
        String memberId = "wnsdhqo";
        String eventCacheId1 = memberId + "_" + System.currentTimeMillis();
        Notification notification1 = createNotification("거래가 성사 되었습니다1", "test/url1");

        emitterRepository.saveEventCache(eventCacheId1, notification1);

        Thread.sleep(100);
        String eventCacheId2 = memberId + "_" + System.currentTimeMillis();
        Notification notification2 = createNotification("거래가 성사 되었습니다2", "test/url2");

        emitterRepository.saveEventCache(eventCacheId2, notification2);

        // When
        Map<String, Object> result = emitterRepository.findAllEventCacheStartWithByMemberId(memberId);

        // Then
        Assertions.assertEquals(2, result.size());
    }

    @Test
    @DisplayName("Id를 통해 Emitter를 Repository에서 제거한다.")
    void deleteById() throws Exception {
        // Given
        String memberId = "wnsdhqo";
        String emitterId = memberId + "_" + System.currentTimeMillis();
        SseEmitter sseEmitter = new SseEmitter(DEFAULT_TIMEOUT);

        // When
        emitterRepository.save(emitterId, sseEmitter);
        emitterRepository.deleteById(emitterId);

        // Then
        Assertions.assertEquals(0, emitterRepository.findAllEmitterStartWithByMemberId(emitterId).size());
    }

    @Test
    @DisplayName("저장된 모든 Emitter를 제거한다.")
    void deleteAllEmitterStartWithId() throws Exception {
        // Given
        String memberId = "wnsdhqo";
        String emitterId1 = memberId + "_" + System.currentTimeMillis();
        emitterRepository.save(emitterId1, new SseEmitter(DEFAULT_TIMEOUT));

        Thread.sleep(100);
        String emitterId2 = memberId + "_" + System.currentTimeMillis();
        emitterRepository.save(emitterId2, new SseEmitter(DEFAULT_TIMEOUT));

        // When
        emitterRepository.deleteAllEmitterStartWithMemberId(memberId);

        // Then
        Assertions.assertEquals(0, emitterRepository.findAllEventCacheStartWithByMemberId(memberId).size());
    }

    @Test
    @DisplayName("캐시에 저장된 모든 Emitter를 제거한다.")
    void deleteAllEventCacheStartWithId() throws Exception {
        // Given
        String memberId = "wnsdhqo";
        String eventCacheId1 = memberId + "_" + System.currentTimeMillis();
        Notification notification1 = createNotification("거래 성사1", "test/url1");

        emitterRepository.saveEventCache(eventCacheId1, notification1);

        Thread.sleep(100);
        String eventCacheId2 = memberId + "_" + System.currentTimeMillis();
        Notification notification2 = createNotification("거래 성사2", "test/url2");

        emitterRepository.saveEventCache(eventCacheId2, notification2);

        // When
        emitterRepository.deleteAllEventCacheStartWithMemberId(memberId);

        // Then
        Assertions.assertEquals(0, emitterRepository.findAllEventCacheStartWithByMemberId(memberId).size());
    }

    private Notification createNotification(String content, String relatedUrl) {
        return Notification.builder()
                .content(content)
                .relatedUrl(relatedUrl)
                .receiver(createMember(1L))
                .notificationType(NotificationType.PAYMENT)
                .build();
    }

    private Member createMember(Long memberNumber) {
        return Member.builder()
                .memberNumber(memberNumber)
                .memberId("wnsdhqo")
                .password("Gkrehd102!")
                .email("wnsdhqo@naver.com")
                .name("배준오")
                .profileImage("/test/url")
                .build();
    }

}