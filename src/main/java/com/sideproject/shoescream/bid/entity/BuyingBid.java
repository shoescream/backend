package com.sideproject.shoescream.bid.entity;

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
public class BuyingBid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "buying_bid_number")
    private Long id;

    @Column(name = "buying_bid_product_size")
    private String size;

    @Column(name = "buying_bid_price")
    private Integer buyingPrice;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "buying_bid_deadline")
    private LocalDateTime buyingBidDeadLine;

    @Column(name = "selling_bid_status")
    private Boolean sellingBidStatus;

    protected BuyingBid() {

    }
}
