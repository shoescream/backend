package com.sideproject.shoescream.product.entity;

import com.sideproject.shoescream.bid.entity.BuyingBid;
import com.sideproject.shoescream.bid.entity.SellingBid;
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
    private Long id;

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

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductImage> images;

    @Column(name = "views")
    private Long views;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    // 사이즈 별 ( + 사이즈 , 색깔 별 ) 최고 가격 최저 가격 중복 처리 필수
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<ProductOption> productOption;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<SellingBid> sellingBids;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<BuyingBid> buyingBids;

    protected Product() {

    }
}
