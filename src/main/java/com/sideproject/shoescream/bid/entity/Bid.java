package com.sideproject.shoescream.bid.entity;

import com.sideproject.shoescream.bid.constant.BidStatus;
import com.sideproject.shoescream.bid.constant.BidType;
import com.sideproject.shoescream.member.entity.Member;
import com.sideproject.shoescream.notification.dto.request.NotificationRequest;
import com.sideproject.shoescream.product.entity.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEventPublisher;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Bid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bid_number")
    private long bidNumber;

    @ManyToOne
    @JoinColumn(name = "product_number", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "member_number", nullable = false)
    private Member member;

    @Column(name = "bid_product_size", nullable = false)
    private String size;

    @Column(name = "bid_price", nullable = false)
    private int bidPrice;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "bid_deadline")
    private LocalDateTime bidDeadLine;

    @Enumerated(EnumType.STRING)
    private BidStatus bidStatus;

    @Enumerated(EnumType.STRING)
    private BidType bidType;

    protected Bid() {

    }

    public void publishEvent(ApplicationEventPublisher eventPublisher, NotificationRequest notificationRequest) {
        System.out.println("하이");
        eventPublisher.publishEvent(notificationRequest);
    }
}
