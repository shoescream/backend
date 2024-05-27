package com.sideproject.shoescream.bid.entity;

import com.sideproject.shoescream.bid.constant.BidStatus;
import com.sideproject.shoescream.bid.constant.BidType;
import com.sideproject.shoescream.member.entity.Member;
import com.sideproject.shoescream.product.entity.Product;
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
public class Bid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bid_number")
    private long id;

    @ManyToOne
    @JoinColumn(name = "product_number")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "member_number")
    private Member member;

    @Column(name = "bid_product_size")
    private String size;

    @Column(name = "bid_price")
    private int bidPrice;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "bid_deadline")
    private LocalDateTime bidDeadLine;

    @Enumerated(EnumType.STRING)
    private BidStatus bidStatus;

    @Enumerated(EnumType.STRING)
    private BidType bidType;

    protected Bid() {

    }
}