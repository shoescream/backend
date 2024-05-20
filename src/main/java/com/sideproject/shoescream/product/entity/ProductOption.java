package com.sideproject.shoescream.product.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
public class ProductOption {

    @Id
    @Column(name = "product_option_number")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_size")
    private Integer size;

    @ManyToOne
    @JoinColumn(name = "product_number")
    private Product product;

    protected ProductOption() {
    }
}
