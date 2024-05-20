package com.sideproject.shoescream.bid.entity;

import com.sideproject.shoescream.bid.constant.SellingType;
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
public class SellingBid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "selling_bid_number")
    private long id;

    @ManyToOne
    @JoinColumn(name = "product_number")
    private Product product;

    @Column(name = "selling_bid_product_size")
    private String size;

    @Column(name = "selling_bid_price")
    private int sellingPrice;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "selling_bid_deadline")
    private LocalDateTime sellingBidDeadLine;

    @Column(name = "selling_bid_status")
    private boolean sellingBidStatus;

    @Enumerated(EnumType.STRING)
    private SellingType sellingType;

    protected SellingBid() {

    }
}
