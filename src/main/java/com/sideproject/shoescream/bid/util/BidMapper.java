package com.sideproject.shoescream.bid.util;

import com.sideproject.shoescream.bid.constant.BidStatus;
import com.sideproject.shoescream.bid.constant.BidType;
import com.sideproject.shoescream.bid.dto.request.BuyingBidRequest;
import com.sideproject.shoescream.bid.dto.request.SellingBidRequest;
import com.sideproject.shoescream.bid.dto.response.BuyingBidResponse;
import com.sideproject.shoescream.bid.dto.response.BuyingProductInfoResponse;
import com.sideproject.shoescream.bid.dto.response.SellingBidResponse;
import com.sideproject.shoescream.bid.dto.response.SellingProductInfoResponse;
import com.sideproject.shoescream.bid.entity.Bid;
import com.sideproject.shoescream.product.entity.ProductOption;

import java.time.LocalDateTime;

public class BidMapper {

    public static SellingProductInfoResponse toSellingProductInfoResponse(ProductOption productOption) {
        return SellingProductInfoResponse.builder()
                .productCode(productOption.getProduct().getProductCode())
                .productName(productOption.getProduct().getProductName())
                .productSubName(productOption.getProduct().getProductSubName())
                .lowestPrice(productOption.getLowestPrice())
                .highestPrice(productOption.getHighestPrice())
                .build();
    }

    public static Bid toSellingBid(SellingBidRequest sellingBidRequest, ProductOption productOption, BidType bidType) {
        LocalDateTime now = LocalDateTime.now();
        return Bid.builder()
                .product(productOption.getProduct())
                .size(sellingBidRequest.size())
                .bidPrice(sellingBidRequest.price())
                .createdAt(now)
                .bidDeadLine(now.plusDays(sellingBidRequest.sellingBidDeadLine()))
                .bidStatus(BidStatus.WAITING_MATCHING)
                .bidType(bidType)
                .build();
    }

    public static SellingBidResponse toSellingBidResponse(Bid bid) {
        return SellingBidResponse.builder()
                .productCode(bid.getProduct().getProductCode())
                .size(bid.getSize())
                .price(bid.getBidPrice())
                .quantity(1)
                .build();
    }

    public static BuyingProductInfoResponse toBuyingProductInfoResponse(ProductOption productOption) {
        return BuyingProductInfoResponse.builder()
                .productCode(productOption.getProduct().getProductCode())
                .productName(productOption.getProduct().getProductName())
                .productSubName(productOption.getProduct().getProductSubName())
                .lowestPrice(productOption.getLowestPrice())
                .highestPrice(productOption.getHighestPrice())
                .build();
    }

    public static Bid toBuyingBid(BuyingBidRequest buyingBidRequest, ProductOption productOption, BidType bidType) {
        LocalDateTime now = LocalDateTime.now();
        return Bid.builder()
                .product(productOption.getProduct())
                .size(buyingBidRequest.size())
                .bidPrice(buyingBidRequest.price())
                .createdAt(now)
                .bidDeadLine(now.plusDays(buyingBidRequest.buyingBidDeadLine()))
                .bidStatus(BidStatus.WAITING_MATCHING)
                .bidType(bidType)
                .build();
    }

    public static BuyingBidResponse toBuyingBidResponse(Bid bid) {
        return BuyingBidResponse.builder()
                .productCode(bid.getProduct().getProductCode())
                .size(bid.getSize())
                .price(bid.getBidPrice())
                .quantity(1)
                .build();
    }
}
