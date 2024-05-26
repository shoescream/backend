package com.sideproject.shoescream.bid.dto.response;

import lombok.Builder;

@Builder
public record BuyingBidResponse(
        String productCode,
        String size,
        int price,
        int quantity
) {
}
