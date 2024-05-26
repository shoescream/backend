package com.sideproject.shoescream.bid.dto.response;

import lombok.Builder;

@Builder
public record SellingBidResponse(
        String productCode,
        String size,
        int price,
        int quantity
) {
}
