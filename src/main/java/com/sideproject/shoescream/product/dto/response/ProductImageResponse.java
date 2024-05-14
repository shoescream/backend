package com.sideproject.shoescream.product.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record ProductImageResponse(
        List<String> productImage
) {
}
