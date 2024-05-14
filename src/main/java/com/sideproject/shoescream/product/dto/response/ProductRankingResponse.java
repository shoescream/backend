package com.sideproject.shoescream.product.dto.response;

import lombok.Builder;

@Builder
public record ProductRankingResponse(
        Long id,
        String productName,
        String productSubName,
        ProductImageResponse productImageResponse,
        String productCode
) {
}
