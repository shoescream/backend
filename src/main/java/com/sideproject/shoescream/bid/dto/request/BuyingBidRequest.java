package com.sideproject.shoescream.bid.dto.request;

public record BuyingBidRequest(
        long productNumber,

        String size,

        int price,

        int buyingBidDeadLine
) {
}
