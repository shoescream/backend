package com.sideproject.shoescream.product.service;

import com.sideproject.shoescream.product.dto.response.*;
import com.sideproject.shoescream.product.entity.Product;
import com.sideproject.shoescream.product.entity.ProductImage;
import com.sideproject.shoescream.product.entity.ProductOption;
import com.sideproject.shoescream.product.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;

@DisplayName("비즈니스 로직 - 상품")
@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @DisplayName("모든 상품 조회 테스트")
    @Test
    void givenNoting_whenFinding_thenReturnProducts() {
        given(productRepository.findAllOrderByCreatedAtAsc()).willReturn(List.of(createProduct(), createProduct()));

        List<ProductResponse> result = productService.getAllProducts();

        assertThat(result).hasSize(2);
        assertThat(result.get(0)).hasFieldOrPropertyWithValue("productNumber", 1L);
        assertThat(result.get(1)).hasFieldOrPropertyWithValue("productNumber", 1L);
    }

    @DisplayName("단일 상품 조회 테스트")
    @Test
    void givenProductNumber_whenFinding_thenReturnProduct() {
        given(productRepository.findById(anyLong())).willReturn(Optional.ofNullable(createProduct()));

        ProductDetailResponse result = productService.getProduct("1");

        assertThat(result)
                .hasFieldOrPropertyWithValue("productResponse", createProductResponse())
                .hasFieldOrPropertyWithValue("productOptionResponse", createProductOptionResponse());
    }

    @DisplayName("타입 별 순위권 상품 조회 테스트")
    @Test
    void givenGenderDetailProductType_whenFinding_thenReturnProducts() {
        given(productRepository.findTop30ByGenderAndCategoryAndProductTypeOrderByViewsDesc(any(), any(), any())).willReturn(List.of(createProduct()));

        List<ProductRankingResponse> result = productService.getProductRankingByType("M", "SNK", "01");

        assertThat(result).hasSize(1);
    }

    private Product createProduct() {
        return Product.builder()
                .productNumber(1L)
                .productCode("M-SNK-000001")
                .productName("test-name")
                .productSubName("test-sub-name")
                .brandName("test-brand")
                .price(1000)
                .brandImage("test/url")
                .productOption(Set.of(createProductOption()))
                .productImages(List.of(createProductImage(), createProductImage()))
                .views(0L)
                .build();
    }

    private ProductOption createProductOption() {
        return ProductOption.builder()
                .productOptionNumber(1L)
                .size("255")
                .lowestPrice(2200)
                .highestPrice(2100)
                .build();
    }


    private ProductResponse createProductResponse() {
        return ProductResponse.builder()
                .productNumber(1L)
                .productCode("M-SNK-000001")
                .productName("test-name")
                .productSubName("test-sub-name")
                .brandName("test-brand")
                .price(1000)
                .brandImage("test/url")
                .productImageResponse(createProductImageResponse())
                .views(1L)
                .build();
    }

    private ProductOptionResponse createProductOptionResponse() {
        return ProductOptionResponse.builder()
                .sizeAndPriceBuyInfo(Map.of("255", 2200))
                .sizeAndPriceSellInfo(Map.of("255", 2100))
                .minBuyInfo(2200)
                .maxSellInfo(2100)
                .build();
    }

    private ProductImage createProductImage() {
        return ProductImage.builder()
                .productImage("test/url")
                .build();
    }

    private ProductImageResponse createProductImageResponse() {
        return ProductImageResponse.builder()
                .productImage(List.of("test/url", "test/url"))
                .build();
    }
}
