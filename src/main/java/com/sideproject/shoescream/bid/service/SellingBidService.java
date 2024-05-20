package com.sideproject.shoescream.bid.service;

import com.sideproject.shoescream.bid.dto.SellingProductInfoResponse;
import com.sideproject.shoescream.bid.repository.SellingBidRepository;
import com.sideproject.shoescream.bid.util.SellingBidMapper;
import com.sideproject.shoescream.product.entity.ProductOption;
import com.sideproject.shoescream.product.repository.ProductImageRepository;
import com.sideproject.shoescream.product.repository.ProductOptionRepository;
import com.sideproject.shoescream.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}