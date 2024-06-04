package com.sideproject.shoescream.product.entity;

import com.sideproject.shoescream.bid.entity.Bid;
import com.sideproject.shoescream.bid.entity.Deal;
import com.sideproject.shoescream.review.entity.Review;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Product {

    @Id
    @Column(name = "product_number")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productNumber;

    @Column(name = "product_code")
    private String productCode;

    @Column(nullable = false, length = 500, name = "product_name")
    private String productName;

    @Column(length = 500, name = "product_sub_name")
    private String productSubName;

    @Column(name = "brand_name")
    private String brandName;

    @Column(name = "product_price")
    private Integer price;

    @Column(name = "brand_image")
    private String brandImage;

    @Column(name = "views")
    private Long views;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Review> reviews;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductImage> productImages;

    // 사이즈 별 ( + 사이즈 , 색깔 별 ) 최고 가격 최저 가격 중복 처리 필수
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<ProductOption> productOption;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Bid> bids;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Deal> deals;

    protected Product() {

    }
}
