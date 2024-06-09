package com.sideproject.shoescream.bid.service;

import com.sideproject.shoescream.bid.constant.BidType;
import com.sideproject.shoescream.bid.dto.request.BuyingBidRequest;
import com.sideproject.shoescream.bid.dto.request.SellingBidRequest;
import com.sideproject.shoescream.bid.dto.response.*;
import com.sideproject.shoescream.bid.entity.Bid;
import com.sideproject.shoescream.bid.repository.BidRepository;
import com.sideproject.shoescream.bid.repository.DealRepository;
import com.sideproject.shoescream.bid.util.BidMapper;
import com.sideproject.shoescream.global.exception.ErrorCode;
import com.sideproject.shoescream.member.entity.Member;
import com.sideproject.shoescream.member.exception.MemberNotFoundException;
import com.sideproject.shoescream.member.repository.MemberRepository;
import com.sideproject.shoescream.notification.constant.NotificationType;
import com.sideproject.shoescream.notification.dto.request.NotificationRequest;
import com.sideproject.shoescream.product.entity.Product;
import com.sideproject.shoescream.product.entity.ProductOption;
import com.sideproject.shoescream.product.exception.ProductNotFoundException;
import com.sideproject.shoescream.product.repository.ProductImageRepository;
import com.sideproject.shoescream.product.repository.ProductOptionRepository;
import com.sideproject.shoescream.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BidService {

    private final BidRepository bidRepository;
    private final ProductRepository productRepository;
    private final ProductOptionRepository productOptionRepository;
    private final MemberRepository memberRepository;
    private final ApplicationEventPublisher eventPublisher;

    public BuyingProductInfoResponse getBuyingProductInfo(Long productNumber, String size) {
        ProductOption product = productOptionRepository.findByProduct_ProductNumberAndSize(productNumber, size);
        return BidMapper.toBuyingProductInfoResponse(product);
    }

    @Transactional
    public BuyingBidResponse buyingBid(BuyingBidRequest buyingBidRequest, String memberId) {
        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
        ProductOption productOption = productOptionRepository.findByProduct_ProductNumberAndSize(buyingBidRequest.productNumber(), buyingBidRequest.size());

        updateSellNowPrice(productOption.getHighestPrice(), buyingBidRequest.price(), productOption);
        return BidMapper.toBuyingBidResponse(bidRepository.save(
                BidMapper.toBuyingBid(
                        buyingBidRequest, member, productOption, BidType.BUY_BID)));
    }


    public SellingProductInfoResponse getSellingProductInfo(Long productNumber, String size) {
        ProductOption product = productOptionRepository.findByProduct_ProductNumberAndSize(productNumber, size);
        return BidMapper.toSellingProductInfoResponse(product);
    }

    @Transactional
    public SellingBidResponse sellingBid(SellingBidRequest sellingBidRequest, String memberId) {
        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
        ProductOption productOption = productOptionRepository.findByProduct_ProductNumberAndSize(sellingBidRequest.productNumber(), sellingBidRequest.size());

        updateBuyNowPrice(productOption.getLowestPrice(), sellingBidRequest.price(), productOption);
        return BidMapper.toSellingBidResponse(bidRepository.save(
                BidMapper.toSellingBid(
                        sellingBidRequest, member, productOption, BidType.SELL_BID)));
    }

    @Transactional
    public SellingBidResponse sellingNow(SellingBidRequest sellingBidRequest, String memberId) {
        Member seller = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
        Bid buyBidInfo = findTargetBuyBidOne(sellingBidRequest);

        notifySellInfo(buyBidInfo, createNotificationRequest(buyBidInfo));
        return BidMapper.toSellingBidResponse(buyBidInfo);
    }

    public BidHistoryResponse getBidHistory(String productNumber, String size) {
        Product product = productRepository.findById(Long.valueOf(productNumber))
                .orElseThrow(() -> new ProductNotFoundException(ErrorCode.PRODUCT_NOT_FOUND));

        return BidMapper.toBidHistoryResponse(product, size);
    }

    private void updateSellNowPrice(int currentSellNowPrice, int buyBidPrice, ProductOption productOption) {
        if (currentSellNowPrice < buyBidPrice) {
            productOption.setHighestPrice(buyBidPrice);
        }
    }

    private void updateBuyNowPrice(int currentBuyNowPrice, int sellBidPrice, ProductOption productOption) {
        if (currentBuyNowPrice > sellBidPrice) {
            productOption.setLowestPrice(sellBidPrice);
        }
    }

    private void notifySellInfo(Bid bid, NotificationRequest notificationRequest) {
        bid.publishEvent(eventPublisher, notificationRequest);
    }

    private NotificationRequest createNotificationRequest(Bid buyBidInfo) {
        return NotificationRequest.builder()
                .receiver(buyBidInfo.getMember())
                .notificationType(NotificationType.PAYMENT)
                .content("결제 알림")
                .relatedUrl("test/url")
                .build();
    }

    private Bid findTargetBuyBidOne(SellingBidRequest sellingBidRequest) {
        return bidRepository.findTargetBidOne(
                sellingBidRequest.productNumber(),
                sellingBidRequest.price(),
                sellingBidRequest.size(),
                BidType.BUY_BID).get(0);
    }
}