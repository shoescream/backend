package com.sideproject.shoescream.product.service;

import com.sideproject.shoescream.product.dto.response.ProductResponse;
import com.sideproject.shoescream.product.entity.Product;
import com.sideproject.shoescream.product.repository.ProductRepository;
import com.sideproject.shoescream.product.util.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAllOrderByCreatedAtAsc();
        return products.stream()
                .map(ProductMapper::toProductResponse)
                .collect(Collectors.toList());
    }

    public ProductResponse getProduct(String productNumber) {
        Product product = productRepository.findById(Long.valueOf(productNumber))
                .orElseThrow(() -> new RuntimeException());
        return ProductMapper.toProductResponse(product);
    }
}
