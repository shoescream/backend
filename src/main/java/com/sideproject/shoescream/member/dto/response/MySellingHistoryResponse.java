package com.sideproject.shoescream.member.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record MySellingHistoryResponse(
        String productName,
        String productImage,
        int price,
        String size,
        LocalDateTime createdAt,
        LocalDateTime deadLine,
        LocalDateTime tradedAt,
        String type,
        String status
) {
}
