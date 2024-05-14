package com.sideproject.shoescream.product.dto.response;

import lombok.Builder;

@Builder
public record ProductDetailResponse(
        ProductResponse productResponse,
        ProductOptionResponse productOptionResponse

) {
}
