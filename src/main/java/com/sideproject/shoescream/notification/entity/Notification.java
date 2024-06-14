package com.sideproject.shoescream.notification.entity;

import com.sideproject.shoescream.member.entity.Member;
import com.sideproject.shoescream.notification.constant.NotificationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_number")
    private Long notificationNumber;

    @Column(name = "domain_number")
    private Long domainNumber;

    @Column(name = "notification_content")
    private String notificationContent;

    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    @ManyToOne
    @JoinColumn(name = "receiver_number")
    private Member receiver;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "is_read")
    private boolean isRead;

    protected Notification() {

    }
}
