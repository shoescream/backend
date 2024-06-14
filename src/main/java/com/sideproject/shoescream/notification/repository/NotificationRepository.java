package com.sideproject.shoescream.notification.repository;

import com.sideproject.shoescream.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query(value = "select n from Notification n where n.receiver.memberId=:memberId order by n.createdAt desc ")
    List<Notification> findNotificationsByReceiverIdOrderByCreatedAtDesc(@Param("memberId") String memberId);
}
