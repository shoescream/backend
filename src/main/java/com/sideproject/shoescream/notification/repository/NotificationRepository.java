package com.sideproject.shoescream.notification.repository;

import com.sideproject.shoescream.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
