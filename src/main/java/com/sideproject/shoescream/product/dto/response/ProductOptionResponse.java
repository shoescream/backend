package com.sideproject.shoescream.product.dto.response;

import lombok.Builder;

import java.util.Map;

@Builder
public record ProductOptionResponse(
        Map<String, Integer> sizeAndPriceBuyInfo,
        Map<String, Integer> sizeAndPriceSellInfo,

        int maxSellInfo,

        int minBuyInfo
) {
}
