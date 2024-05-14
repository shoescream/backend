package com.sideproject.shoescream.product.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_image_number")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_number")
    private Product product;

    @Column(length = 500, name = "image_url")
    private String productImage;

    protected ProductImage() {

    }
}
