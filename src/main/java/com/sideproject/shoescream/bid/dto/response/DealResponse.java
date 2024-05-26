package com.sideproject.shoescream.bid.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record DealResponse(
        String size,
        int price,
        LocalDateTime tradedAt
) {
}
