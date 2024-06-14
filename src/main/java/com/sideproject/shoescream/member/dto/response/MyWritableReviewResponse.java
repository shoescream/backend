package com.sideproject.shoescream.member.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record MyWritableReviewResponse(
        long productNumber,
        String productName,
        String productSubName,
        String productImage,
        String dealSize,
        int dealPrice,
        LocalDateTime writeDeadLine
) {
}
