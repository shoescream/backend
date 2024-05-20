package com.sideproject.shoescream.bid.service;

import com.sideproject.shoescream.bid.dto.BuyingProductInfoResponse;
import com.sideproject.shoescream.bid.repository.BuyingBidRepository;
import com.sideproject.shoescream.bid.util.BuyingBidMapper;
import com.sideproject.shoescream.product.entity.ProductOption;
import com.sideproject.shoescream.product.repository.ProductImageRepository;
import com.sideproject.shoescream.product.repository.ProductOptionRepository;
import com.sideproject.shoescream.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
