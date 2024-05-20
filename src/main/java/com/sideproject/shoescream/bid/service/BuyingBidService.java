package com.sideproject.shoescream.bid.service;

import com.sideproject.shoescream.bid.constant.BuyingType;
import com.sideproject.shoescream.bid.dto.request.BuyingBidRequest;
import com.sideproject.shoescream.bid.dto.response.BuyingBidResponse;
import com.sideproject.shoescream.bid.dto.response.BuyingProductInfoResponse;
import com.sideproject.shoescream.bid.repository.BuyingBidRepository;
import com.sideproject.shoescream.bid.util.BuyingBidMapper;
import com.sideproject.shoescream.product.entity.ProductOption;
import com.sideproject.shoescream.product.repository.ProductImageRepository;
import com.sideproject.shoescream.product.repository.ProductOptionRepository;
import com.sideproject.shoescream.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BuyingBidService {

    private final BuyingBidRepository buyingBidRepository;
    private final ProductRepository productRepository;
    private final ProductOptionRepository productOptionRepository;
    private final ProductImageRepository productImageRepository;

    public BuyingProductInfoResponse getBuyingProductInfo(Long productNumber, String size) {
        ProductOption product = productOptionRepository.findByProductIdAndSize(productNumber, size);
        return BuyingBidMapper.toBuyingProductInfoResponse(product);
    }

    @Transactional
    public BuyingBidResponse buyingBid(BuyingBidRequest buyingBidRequest) {
        ProductOption productOption = productOptionRepository.findByProductId(buyingBidRequest.productNumber());

        if (buyingBidRequest.price() == productOption.getLowestPrice()) {
            return BuyingBidMapper.toBuyingBidResponse(buyingBidRepository.save(
                    BuyingBidMapper.toBuyingBid(
                            buyingBidRequest, productOption, BuyingType.BUY_NOW)));
        }

        return BuyingBidMapper.toBuyingBidResponse(buyingBidRepository.save(
                BuyingBidMapper.toBuyingBid(
                        buyingBidRequest, productOption, BuyingType.BUY_BID)));

    }
}
