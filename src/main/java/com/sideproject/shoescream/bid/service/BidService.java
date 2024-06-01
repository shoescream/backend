package com.sideproject.shoescream.bid.service;

import com.sideproject.shoescream.bid.constant.BidStatus;
import com.sideproject.shoescream.bid.constant.BidType;
import com.sideproject.shoescream.bid.constant.DealStatus;
import com.sideproject.shoescream.bid.dto.request.BuyingBidRequest;
import com.sideproject.shoescream.bid.dto.request.SellingBidRequest;
import com.sideproject.shoescream.bid.dto.response.*;
import com.sideproject.shoescream.bid.entity.Bid;
import com.sideproject.shoescream.bid.repository.BidRepository;
import com.sideproject.shoescream.bid.repository.DealRepository;
import com.sideproject.shoescream.bid.util.BidMapper;
import com.sideproject.shoescream.bid.util.DealMapper;
import com.sideproject.shoescream.global.exception.ErrorCode;
import com.sideproject.shoescream.member.entity.Member;
import com.sideproject.shoescream.member.exception.MemberNotFoundException;
import com.sideproject.shoescream.member.repository.MemberRepository;
import com.sideproject.shoescream.product.entity.Product;
import com.sideproject.shoescream.product.entity.ProductOption;
import com.sideproject.shoescream.product.repository.ProductImageRepository;
import com.sideproject.shoescream.product.repository.ProductOptionRepository;
import com.sideproject.shoescream.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BidService {

    private final BidRepository bidRepository;
    private final ProductRepository productRepository;
    private final ProductOptionRepository productOptionRepository;
    private final ProductImageRepository productImageRepository;
    private final DealRepository dealRepository;
    private final MemberRepository memberRepository;

    public SellingProductInfoResponse getSellingProductInfo(Long productNumber, String size) {
        ProductOption product = productOptionRepository.findByProduct_ProductNumberAndSize(productNumber, size);
        return BidMapper.toSellingProductInfoResponse(product);
    }

    public BuyingProductInfoResponse getBuyingProductInfo(Long productNumber, String size, Authentication authentication) {
        ProductOption product = productOptionRepository.findByProduct_ProductNumberAndSize(productNumber, size);
        return BidMapper.toBuyingProductInfoResponse(product);
    }

    public BidHistoryResponse getBidHistory(String productNumber, String size) {
        Product product = productRepository.findById(Long.valueOf(productNumber))
                .orElseThrow(() -> new RuntimeException());

        return BidMapper.toBidHistoryResponse(product, size);
    }

    @Transactional
    public SellingBidResponse sellingBid(SellingBidRequest sellingBidRequest, Authentication authentication) {
        Member member = memberRepository.findByMemberId(authentication.getName())
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
        ProductOption productOption = productOptionRepository.findByProduct_ProductNumberAndSize(sellingBidRequest.productNumber(), sellingBidRequest.size());
        Optional<Bid> buyBid = bidRepository.findTargetBidOne(productOption.getProduct().getProductNumber(), sellingBidRequest.price(), BidType.BUY_BID);

        if (buyBid.isEmpty()) {
            updateBuyNowPrice(productOption.getLowestPrice(), sellingBidRequest.price(), productOption);
            return BidMapper.toSellingBidResponse(bidRepository.save(
                    BidMapper.toSellingBid(
                            sellingBidRequest, member, productOption, BidType.SELL_BID)));
        }

        buyBid.get().setBidStatus(BidStatus.COMPLETE_MATCHING);
        dealRepository.save(DealMapper.sellingBidToDeal(sellingBidRequest, productOption, buyBid.get(), member.getMemberNumber(), DealStatus.WAITING_DEPOSIT));
        return BidMapper.toSellingBidResponse(BidMapper.toSellingBid(sellingBidRequest, member, productOption, BidType.SELL_NOW));
    }

    @Transactional
    public BuyingBidResponse buyingBid(BuyingBidRequest buyingBidRequest, Authentication authentication) {
        Member member = memberRepository.findByMemberId(authentication.getName())
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
        ProductOption productOption = productOptionRepository.findByProduct_ProductNumberAndSize(buyingBidRequest.productNumber(), buyingBidRequest.size());
        Optional<Bid> sellBid = bidRepository.findTargetBidOne(productOption.getProduct().getProductNumber(), buyingBidRequest.price(), BidType.SELL_BID);

        if (sellBid.isEmpty()) {
            updateSellNowPrice(productOption.getHighestPrice(), buyingBidRequest.price(), productOption);
            return BidMapper.toBuyingBidResponse(bidRepository.save(
                    BidMapper.toBuyingBid(
                            buyingBidRequest, member, productOption, BidType.BUY_BID)));
        }

        sellBid.get().setBidStatus(BidStatus.COMPLETE_MATCHING);
        dealRepository.save(DealMapper.buyingBidToDeal(buyingBidRequest, productOption, sellBid.get(), member.getMemberNumber(), DealStatus.WAITING_DEPOSIT));
        return BidMapper.toBuyingBidResponse(BidMapper.toBuyingBid(buyingBidRequest, member, productOption, BidType.BUY_NOW));
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
}