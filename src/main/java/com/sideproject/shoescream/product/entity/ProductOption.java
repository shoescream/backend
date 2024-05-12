package com.sideproject.shoescream.product.entity;

import com.sideproject.shoescream.product.constant.SizeType;
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

    @Enumerated(EnumType.STRING)
    private SizeType size;

    @OneToOne
    @JoinColumn(name = "product_number")
    private Product product;

    protected ProductOption() {
    }
}
