package com.sideproject.shoescream.product.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ProductResponse(
        Long id,
        String productCode,
        String productName,
        String productSubName,
        String brandName,
        Integer price,
        String brandImage,
        ProductImageResponse productImageResponse,
        LocalDateTime createdAt,
        Long views) {
}
