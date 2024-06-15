package com.sideproject.shoescream.bid.dto.response;

import lombok.Builder;

@Builder
public record BuyingProductInfoResponse(
        long bidNumber,

        String productCode,

        String productName,

        String productSubName,

        String productImage,

        int lowestPrice,

        int highestPrice
) {
}
