package com.sideproject.shoescream.product.service;

import com.sideproject.shoescream.product.dto.response.ProductDetailResponse;
import com.sideproject.shoescream.product.dto.response.ProductRankingResponse;
import com.sideproject.shoescream.product.dto.response.ProductResponse;
import com.sideproject.shoescream.product.entity.Product;
import com.sideproject.shoescream.product.repository.ProductRepository;
import com.sideproject.shoescream.product.util.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public ProductDetailResponse getProduct(String productNumber) {
        Product product = productRepository.findById(Long.valueOf(productNumber))
                .orElseThrow(() -> new RuntimeException());

        product.setViews(product.getViews() + 1);
        productRepository.save(product);

        return ProductMapper.toProductDetailResponse(product);
    }

    public List<ProductRankingResponse> getProductRankingByType(String gender, String detail, String productType) {
        List<Product> products = productRepository.findTop30ByGenderAndCategoryAndProductTypeOrderByViewsDesc(gender, detail, productType);
        return products.stream()
                .map(ProductMapper::toProductRankingResponse)
                .collect(Collectors.toList());
    }
}
