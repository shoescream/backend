package com.sideproject.shoescream.bid.dto;

import lombok.Builder;

@Builder
public record BuyingProductInfoResponse(
        String productCode,

        String productName,

        String productSubName,

        Integer lowestPrice,

        Integer highestPrice
) {
}
