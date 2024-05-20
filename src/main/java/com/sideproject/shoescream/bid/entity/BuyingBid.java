package com.sideproject.shoescream.bid.entity;

import com.sideproject.shoescream.bid.constant.BuyingType;
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
public class BuyingBid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "buying_bid_number")
    //TODO long and Long JpaRepository사용할 때 Long타입으로 오토 박싱(성능 이슈 생각해보기) 일단 비교 연산자 == 사용하기 위해 원시 타입 쓰기
    private long id;

    @ManyToOne
    @JoinColumn(name = "product_number")
    private Product product;

    @Column(name = "buying_bid_product_size")
    private String size;

    @Column(name = "buying_bid_price")
    private int buyingPrice;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "buying_bid_deadline")
    private LocalDateTime buyingBidDeadLine;

    @Column(name = "buying_bid_status")
    private boolean buyingBidStatus;

    @Enumerated(EnumType.STRING)
    private BuyingType buyingType;

    protected BuyingBid() {

    }
}
