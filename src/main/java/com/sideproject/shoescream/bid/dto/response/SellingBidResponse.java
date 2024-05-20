package com.sideproject.shoescream.bid.dto.response;

import lombok.Builder;

@Builder
public record SellingBidResponse(
        String productCode,

        int price
) {
}
