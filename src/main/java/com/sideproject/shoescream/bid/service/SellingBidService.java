package com.sideproject.shoescream.bid.service;

import com.sideproject.shoescream.bid.constant.SellingType;
import com.sideproject.shoescream.bid.dto.request.SellingBidRequest;
import com.sideproject.shoescream.bid.dto.response.SellingBidResponse;
import com.sideproject.shoescream.bid.dto.response.SellingProductInfoResponse;
import com.sideproject.shoescream.bid.repository.SellingBidRepository;
import com.sideproject.shoescream.bid.util.SellingBidMapper;
import com.sideproject.shoescream.product.entity.ProductOption;
import com.sideproject.shoescream.product.repository.ProductImageRepository;
import com.sideproject.shoescream.product.repository.ProductOptionRepository;
import com.sideproject.shoescream.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SellingBidService {

    private final SellingBidRepository sellingBidRepository;
    private final ProductRepository productRepository;
    private final ProductOptionRepository productOptionRepository;
    private final ProductImageRepository productImageRepository;

    public SellingProductInfoResponse getSellingProductInfo(Long productNumber, String size) {
        ProductOption product = productOptionRepository.findByProductIdAndSize(productNumber, size);
        return SellingBidMapper.toSellingProductInfoResponse(product);
    }

    @Transactional
    public SellingBidResponse sellingBid(SellingBidRequest sellingBidRequest) {
        ProductOption productOption = productOptionRepository.findByProductId(sellingBidRequest.productNumber());

        if (sellingBidRequest.price() == productOption.getHighestPrice()) {
            return SellingBidMapper.toSellingBidResponse(sellingBidRepository.save(
                    SellingBidMapper.toSellingBid(
                            sellingBidRequest, productOption, SellingType.SELL_NOW)));

        }

        return SellingBidMapper.toSellingBidResponse(sellingBidRepository.save(
                SellingBidMapper.toSellingBid(
                        sellingBidRequest, productOption, SellingType.SELL_BID)));
    }
}