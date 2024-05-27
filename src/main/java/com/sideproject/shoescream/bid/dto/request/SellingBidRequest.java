package com.sideproject.shoescream.bid.dto.request;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record SellingBidRequest(
        long productNumber,

        String size,

        int price,

        int sellingBidDeadLine

) {
}
