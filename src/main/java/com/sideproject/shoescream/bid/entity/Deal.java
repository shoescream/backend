package com.sideproject.shoescream.bid.entity;

import com.sideproject.shoescream.bid.constant.DealStatus;
import com.sideproject.shoescream.member.entity.Member;
import com.sideproject.shoescream.product.entity.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

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
    private long dealNumber;

    @Column(name = "buyer_number")
    private long buyerNumber;

    @Column(name = "seller_number")
    private long sellerNumber;

    @ManyToOne
    @JoinColumn(name = "product_number")
    private Product product;

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

    @Column(name = "is_write_review")
    private Boolean isWriteReview;

    protected Deal() {

    }
}
