package com.sideproject.shoescream.product.dto.response;

import com.sideproject.shoescream.product.entity.Product;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ProductResponse(
        String productName,
        String productSubName,
        String brandName,
        String productImage,
        LocalDateTime createdAt,
        Long views) {
}
