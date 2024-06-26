package com.sideproject.shoescream.bid.service;

import com.sideproject.shoescream.bid.dto.response.DealHistoryResponse;
import com.sideproject.shoescream.bid.dto.response.QuoteResponse;
import com.sideproject.shoescream.bid.entity.Deal;
import com.sideproject.shoescream.bid.repository.DealRepository;
import com.sideproject.shoescream.bid.util.DealMapper;
import com.sideproject.shoescream.global.exception.ErrorCode;
import com.sideproject.shoescream.product.entity.Product;
import com.sideproject.shoescream.product.exception.ProductNotFoundException;
import com.sideproject.shoescream.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DealService {

    private final DealRepository dealRepository;
    private final ProductRepository productRepository;

    public QuoteResponse getQuote(String productNumber, String size, int period) {
        List<Deal> deals = dealRepository.findByProduct_ProductNumber(Long.valueOf(productNumber));
        return DealMapper.toQuoteResponse(deals, size, period);
    }

    public DealHistoryResponse getDealHistoryResponse(String productNumber, String size) {
        Product product = productRepository.findById(Long.valueOf(productNumber))
                .orElseThrow(() -> new ProductNotFoundException(ErrorCode.PRODUCT_NOT_FOUND));
        return DealMapper.toDealHistoryResponse(product, size);
    }
}
