package com.sideproject.shoescream.bid.entity;

import com.sideproject.shoescream.bid.constant.DealStatus;
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
public class Deal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deal_number")
    private long id;

    @ManyToOne
    @JoinColumn(name = "product_number")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "member_number")
    private Member member;

    @Column(name = "deal_size")
    private String size;

    @Column(name = "deal_price")
    private int price;

    @Enumerated(EnumType.STRING)
    private DealStatus dealStatus;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "traded_at")
    private LocalDateTime tradedAt;

    protected Deal() {

    }
}