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
    private long id;

    @Column(name = "product_size")
    private String size;

    // lowerPrice >= highestPrice 판매자는 높은 금액으로 팔려 함, 구매자는 낮은 금액으로 사려 함
    // 판매자가 올린 제일 낮은 가격 => 즉시 구매, 더 낮은 금액 => 구매 입찰
    @Column(name = "lower_price")
    private int lowestPrice;

    // 구매자가 올린 제일 높은 가격 => 즉시 판매, 더 높은 금액 => 판매 입찰
    @Column(name = "high_price")
    private int highestPrice;

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
