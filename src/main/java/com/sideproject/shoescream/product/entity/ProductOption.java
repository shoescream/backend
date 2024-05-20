package com.sideproject.shoescream.product.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

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
    private String size;

    @Column(name = "lower_price")
    private Integer lowestPrice;

    @Column(name = "high_price")
    private Integer highestPrice;

    @ManyToOne
    @JoinColumn(name = "product_number")
    private Product product;

    protected ProductOption() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductOption that = (ProductOption) o;
        return Objects.equals(product.getId(), that.product.getId()) &&
                Objects.equals(size, that.size) &&
                Objects.equals(lowestPrice, that.lowestPrice) &&
                Objects.equals(highestPrice, that.highestPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product.getId(), size, lowestPrice, highestPrice);
    }
}
