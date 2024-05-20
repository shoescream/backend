package com.sideproject.shoescream.bid.util;

import com.sideproject.shoescream.bid.dto.SellingProductInfoResponse;
import com.sideproject.shoescream.product.entity.ProductOption;

public class SellingBidMapper {

    public static SellingProductInfoResponse toSellingProductInfoResponse(ProductOption productOption) {
        return SellingProductInfoResponse.builder()
                .productCode(productOption.getProduct().getProductCode())
                .productName(productOption.getProduct().getProductName())
                .productSubName(productOption.getProduct().getProductSubName())
                .lowestPrice(productOption.getLowestPrice())
                .highestPrice(productOption.getHighestPrice())
                .build();
    }
}
