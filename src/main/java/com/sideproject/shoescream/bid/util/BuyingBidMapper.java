package com.sideproject.shoescream.bid.util;

import com.sideproject.shoescream.bid.dto.BuyingProductInfoResponse;
import com.sideproject.shoescream.product.entity.ProductOption;

public class BuyingBidMapper {

    public static BuyingProductInfoResponse toBuyingProductInfoResponse(ProductOption productOption) {
        return BuyingProductInfoResponse.builder()
                .productCode(productOption.getProduct().getProductCode())
                .productName(productOption.getProduct().getProductName())
                .productSubName(productOption.getProduct().getProductSubName())
                .lowestPrice(productOption.getLowestPrice())
                .highestPrice(productOption.getHighestPrice())
                .build();
    }
}
