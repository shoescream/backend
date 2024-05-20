package com.sideproject.shoescream.bid.dto.response;

import lombok.Builder;

@Builder
public record BuyingBidResponse(
        String productCode,

        int price
) {
}
