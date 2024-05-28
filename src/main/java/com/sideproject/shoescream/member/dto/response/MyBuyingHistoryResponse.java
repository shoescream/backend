package com.sideproject.shoescream.member.dto.response;

import com.sideproject.shoescream.bid.entity.Bid;
import com.sideproject.shoescream.bid.entity.Deal;
import com.sideproject.shoescream.product.entity.ProductImage;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record MyBuyingHistoryResponse(

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
