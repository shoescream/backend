package com.sideproject.shoescream.bid.service;

import com.sideproject.shoescream.bid.constant.BidType;
import com.sideproject.shoescream.bid.dto.request.BuyingBidRequest;
import com.sideproject.shoescream.bid.dto.request.SellingBidRequest;
import com.sideproject.shoescream.bid.dto.response.BuyingBidResponse;
import com.sideproject.shoescream.bid.dto.response.BuyingProductInfoResponse;
import com.sideproject.shoescream.bid.dto.response.SellingBidResponse;
import com.sideproject.shoescream.bid.dto.response.SellingProductInfoResponse;
import com.sideproject.shoescream.bid.repository.BidRepository;
import com.sideproject.shoescream.bid.util.BidMapper;
import com.sideproject.shoescream.product.entity.ProductOption;
import com.sideproject.shoescream.product.repository.ProductImageRepository;
import com.sideproject.shoescream.product.repository.ProductOptionRepository;
import com.sideproject.shoescream.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BidService {

    private final BidRepository bidRepository;
    private final ProductRepository productRepository;
    private final ProductOptionRepository productOptionRepository;
    private final ProductImageRepository productImageRepository;

    public SellingProductInfoResponse getSellingProductInfo(Long productNumber, String size) {
        ProductOption product = productOptionRepository.findByProductIdAndSize(productNumber, size);
        return BidMapper.toSellingProductInfoResponse(product);
    }

    @Transactional
    public SellingBidResponse sellingBid(SellingBidRequest sellingBidRequest) {
        ProductOption productOption = productOptionRepository.findByProductId(sellingBidRequest.productNumber());

        if (sellingBidRequest.price() == productOption.getHighestPrice()) {
            return BidMapper.toSellingBidResponse(bidRepository.save(
                    BidMapper.toSellingBid(
                            sellingBidRequest, productOption, BidType.SELL_NOW)));

        }

        return BidMapper.toSellingBidResponse(bidRepository.save(
                BidMapper.toSellingBid(
                        sellingBidRequest, productOption, BidType.SELL_BID)));
    }

    public BuyingProductInfoResponse getBuyingProductInfo(Long productNumber, String size) {
        ProductOption product = productOptionRepository.findByProductIdAndSize(productNumber, size);
        return BidMapper.toBuyingProductInfoResponse(product);
    }

    @Transactional
    public BuyingBidResponse buyingBid(BuyingBidRequest buyingBidRequest) {
        ProductOption productOption = productOptionRepository.findByProductId(buyingBidRequest.productNumber());

        if (buyingBidRequest.price() == productOption.getLowestPrice()) {
            return BidMapper.toBuyingBidResponse(bidRepository.save(
                    BidMapper.toBuyingBid(
                            buyingBidRequest, productOption, BidType.BUY_NOW)));
        }

        return BidMapper.toBuyingBidResponse(bidRepository.save(
                BidMapper.toBuyingBid(
                        buyingBidRequest, productOption, BidType.BUY_BID)));
    }
}