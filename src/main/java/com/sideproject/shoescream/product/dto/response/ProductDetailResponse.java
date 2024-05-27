package com.sideproject.shoescream.product.dto.response;

import com.sideproject.shoescream.bid.dto.response.BuyingBidResponse;
import com.sideproject.shoescream.bid.dto.response.DealResponse;
import com.sideproject.shoescream.bid.dto.response.SellingBidResponse;
import lombok.Builder;

import java.util.List;

@Builder
public record ProductDetailResponse(
        ProductResponse productResponse,
        ProductOptionResponse productOptionResponse
) {
}
