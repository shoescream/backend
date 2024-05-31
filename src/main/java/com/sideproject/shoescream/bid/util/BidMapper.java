package com.sideproject.shoescream.bid.util;

import com.sideproject.shoescream.bid.constant.BidStatus;
import com.sideproject.shoescream.bid.constant.BidType;
import com.sideproject.shoescream.bid.dto.request.BuyingBidRequest;
import com.sideproject.shoescream.bid.dto.request.SellingBidRequest;
import com.sideproject.shoescream.bid.dto.response.*;
import com.sideproject.shoescream.bid.entity.Bid;
import com.sideproject.shoescream.member.entity.Member;
import com.sideproject.shoescream.product.entity.Product;
import com.sideproject.shoescream.product.entity.ProductOption;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

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

    public static Bid toSellingBid(SellingBidRequest sellingBidRequest, Member member, ProductOption productOption, BidType bidType) {
        LocalDateTime now = LocalDateTime.now();
        return Bid.builder()
                .product(productOption.getProduct())
                .member(member)
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
                .size(bid.getSize())
                .price(bid.getBidPrice())
                .quantity(1)
                .createdAt(bid.getCreatedAt())
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

    public static Bid toBuyingBid(BuyingBidRequest buyingBidRequest, Member member, ProductOption productOption, BidType bidType) {
        LocalDateTime now = LocalDateTime.now();
        return Bid.builder()
                .product(productOption.getProduct())
                .member(member)
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
                .size(bid.getSize())
                .price(bid.getBidPrice())
                .quantity(1)
                .createdAt(bid.getCreatedAt())
                .build();
    }

    public static BidHistoryResponse toBidHistoryResponse(Product product, String size) {
        if ("allSize".equals(size)) {
            return BidHistoryResponse.builder()
                    .buyingBidResponse(aggregateBuyingBidResponse(product, size))
                    .sellingBidResponse(aggregateSellBidResponse(product, size))
                    .build();
        }
        return BidHistoryResponse.builder()
                .buyingBidResponse(aggregateBuyingBidResponseForSpecificSize(product, size))
                .sellingBidResponse(aggregateSellBidResponseForSpecificSize(product, size))
                .build();
    }

    private static List<BuyingBidResponse> aggregateBuyingBidResponse(Product product, String size) {
        return product.getBids().stream()
                .filter(bid -> bid.getBidType().equals(BidType.BUY_BID))
                .sorted(Comparator.comparing(Bid::getCreatedAt).reversed())
                .map(BidMapper::toBuyingBidResponse)
                .limit(5)
                .toList();
    }

    private static List<SellingBidResponse> aggregateSellBidResponse(Product product, String size) {
        return product.getBids().stream()
                .filter(bid -> bid.getBidType().equals(BidType.SELL_BID))
                .sorted(Comparator.comparing(Bid::getCreatedAt).reversed())
                .map(BidMapper::toSellingBidResponse)
                .limit(5)
                .toList();
    }

    private static List<BuyingBidResponse> aggregateBuyingBidResponseForSpecificSize(Product product, String size) {
        return product.getBids().stream()
                .filter(bid -> bid.getBidType().equals(BidType.BUY_BID))
                .filter(bid -> bid.getSize().equals(size))
                .sorted(Comparator.comparing(Bid::getCreatedAt).reversed())
                .map(BidMapper::toBuyingBidResponse)
                .limit(5)
                .toList();
    }

    private static List<SellingBidResponse> aggregateSellBidResponseForSpecificSize(Product product, String size) {
        return product.getBids().stream()
                .filter(bid -> bid.getBidType().equals(BidType.SELL_BID))
                .filter(bid -> bid.getSize().equals(size))
                .sorted(Comparator.comparing(Bid::getCreatedAt).reversed())
                .map(BidMapper::toSellingBidResponse)
                .limit(5)
                .toList();
    }

}
