package com.sideproject.shoescream.bid.util;

import com.sideproject.shoescream.bid.constant.BuyingType;
import com.sideproject.shoescream.bid.dto.request.BuyingBidRequest;
import com.sideproject.shoescream.bid.dto.response.BuyingBidResponse;
import com.sideproject.shoescream.bid.dto.response.BuyingProductInfoResponse;
import com.sideproject.shoescream.bid.dto.response.SellingBidResponse;
import com.sideproject.shoescream.bid.entity.BuyingBid;
import com.sideproject.shoescream.bid.entity.SellingBid;
import com.sideproject.shoescream.product.entity.ProductOption;

import java.time.LocalDateTime;

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

    public static BuyingBid toBuyingBid(BuyingBidRequest buyingBidRequest, ProductOption productOption, BuyingType buyingType) {
        LocalDateTime now = LocalDateTime.now();
        return BuyingBid.builder()
                .product(productOption.getProduct())
                .size(buyingBidRequest.size())
                .buyingPrice(buyingBidRequest.price())
                .createdAt(now)
                .buyingBidDeadLine(now.plusDays(buyingBidRequest.buyingBidDeadLine()))
                .buyingBidStatus(false)
                .buyingType(buyingType)
                .build();
    }

    public static BuyingBidResponse toBuyingBidResponse(BuyingBid buyingBid) {
        return BuyingBidResponse.builder()
                .productCode(buyingBid.getProduct().getProductCode())
                .price(buyingBid.getBuyingPrice())
                .build();
    }
}
