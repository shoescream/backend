package com.sideproject.shoescream.bid.service;

import com.sideproject.shoescream.bid.dto.response.DealHistoryResponse;
import com.sideproject.shoescream.bid.dto.response.QuoteResponse;
import com.sideproject.shoescream.bid.entity.Deal;
import com.sideproject.shoescream.bid.repository.DealRepository;
import com.sideproject.shoescream.bid.util.DealMapper;
import com.sideproject.shoescream.product.entity.Product;
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
        List<Deal> deals = dealRepository.findByProductId(Long.valueOf(productNumber));
        return DealMapper.toQuoteResponse(deals, size, period);
    }

    public DealHistoryResponse getDealHistoryResponse(String productNumber, String size) {
        Product product = productRepository.findById(Long.valueOf(productNumber))
                .orElseThrow(() -> new RuntimeException());

        return DealMapper.toDealHistoryResponse(product, size);
    }
}
