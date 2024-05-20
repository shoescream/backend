package com.sideproject.shoescream.bid.dto.response;

import lombok.Builder;

@Builder
public record BuyingProductInfoResponse(
        String productCode,

        String productName,

        String productSubName,

        int lowestPrice,

        int highestPrice
) {
}
