package com.sideproject.shoescream.notification.repository;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;

public interface EmitterRepository {

    SseEmitter save(String emitterId, SseEmitter sseEmitter);
    void saveEventCache(String emitterId, Object event);
    Map<String, SseEmitter> findAllEmitterStartWithByMemberNumber(String memberNumber);
    Map<String, Object> findAllEventCacheStartWithByMemberNumber(String memberNumber);
    void deleteById(String id);
    void deleteAllEmitterStartWithMemberId(String memberId);
    void deleteAllEventCacheStartWithMemberId(String memberId);
}
