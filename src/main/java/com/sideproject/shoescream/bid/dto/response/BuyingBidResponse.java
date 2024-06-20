package com.sideproject.shoescream.bid.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record BuyingBidResponse(
        String productName,
        String productImage,
        String size,
        int price,
        int quantity,
        LocalDateTime createdAt
) {
}
