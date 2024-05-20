package com.sideproject.shoescream.bid.dto.request;

import java.time.LocalDateTime;

public record BuyingBidRequest(
        long productNumber,

        String size,

        int price,

        int buyingBidDeadLine
) {
}
