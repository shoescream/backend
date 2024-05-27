package com.sideproject.shoescream.bid.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record BidHistoryResponse(
        List<SellingBidResponse> sellingBidResponse,
        List<BuyingBidResponse> buyingBidResponse
) {
}
