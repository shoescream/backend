package com.sideproject.shoescream.product.entity;

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
public class Product {

    @Id
    @Column(name = "product_number")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500, name = "product_name")
    private String productName;

    @Column(length = 500, name = "product_sub_name")
    private String productSubName;

    @Column(name = "brand_name")
    private String brandName;

    @Column(length = 500, name = "product_image")
    private String productImage;

    @Column(name = "views")
    private Long views;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    protected Product() {

    }
}
