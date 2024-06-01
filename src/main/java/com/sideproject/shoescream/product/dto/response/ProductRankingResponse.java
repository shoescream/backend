package com.sideproject.shoescream.product.dto.response;

import lombok.Builder;

@Builder
public record ProductRankingResponse(
        Long productNumber,
        String productName,
        String productSubName,
        String brandName,
        Integer price,
        ProductImageResponse productImageResponse,
        String productCode
) {
}
