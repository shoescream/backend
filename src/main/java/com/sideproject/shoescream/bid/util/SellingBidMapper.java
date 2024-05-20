package com.sideproject.shoescream.bid.util;

import com.sideproject.shoescream.bid.constant.SellingType;
import com.sideproject.shoescream.bid.dto.request.SellingBidRequest;
import com.sideproject.shoescream.bid.dto.response.SellingBidResponse;
import com.sideproject.shoescream.bid.dto.response.SellingProductInfoResponse;
import com.sideproject.shoescream.bid.entity.SellingBid;
import com.sideproject.shoescream.product.entity.Product;
import com.sideproject.shoescream.product.entity.ProductOption;

import java.time.LocalDateTime;

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

    public static SellingBid toSellingBid(SellingBidRequest sellingBidRequest, ProductOption productOption, SellingType sellingType) {
        LocalDateTime now = LocalDateTime.now();
        return SellingBid.builder()
                .product(productOption.getProduct())
                .size(sellingBidRequest.size())
                .sellingPrice(sellingBidRequest.price())
                .createdAt(now)
                .sellingBidDeadLine(now.plusDays(sellingBidRequest.sellingBidDeadLine()))
                .sellingBidStatus(false)
                .sellingType(sellingType)
                .build();
    }

    public static SellingBidResponse toSellingBidResponse(SellingBid sellingBid) {
        return SellingBidResponse.builder()
                .productCode(sellingBid.getProduct().getProductCode())
                .price(sellingBid.getSellingPrice())
                .build();
    }
}
