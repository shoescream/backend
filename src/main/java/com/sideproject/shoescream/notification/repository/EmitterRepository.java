package com.sideproject.shoescream.notification.repository;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;

public interface EmitterRepository {

    SseEmitter save(String emitterId, SseEmitter sseEmitter);
    void saveEventCache(String emitterId, Object event);
    Map<String, SseEmitter> findAllEmitterStartWithByMemberId(String receiverId);
    Map<String, Object> findAllEventCacheStartWithByMemberId(String receiverId);
    void deleteById(String id);
    void deleteAllEmitterStartWithMemberId(String memberId);
    void deleteAllEventCacheStartWithMemberId(String memberId);
}
