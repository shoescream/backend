package com.sideproject.shoescream.product.controller;

import com.sideproject.shoescream.product.dto.response.*;
import com.sideproject.shoescream.product.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.mockito.BDDMockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@MockBean(JpaMetamodelMappingContext.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;


    @Test
    @DisplayName("[GET] 상품 전체 조회 컨트롤러 테스트")
    @WithMockUser
    void 상품_전체조회_컨트롤러_테스트() throws Exception {
        List<ProductResponse> response = List.of(createProductResponse(), createProductResponse());

        given(productService.getAllProducts()).willReturn(response);

        mockMvc.perform(get("/products")
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("[GET] 상품 단건 조회 컨트롤러 테스트")
    @WithMockUser
    void 상품_단건조회_컨트롤러_테스트() throws Exception {
        String request = "1";
        ProductDetailResponse response = createProductDetailResponse();
        given(productService.getProduct(any())).willReturn(response);

        mockMvc.perform(get("/products/{productNumber}", request)
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("[GET] 랭킹 페이지 조회 컨트롤러 테스트")
    @WithMockUser
    void 상품_랭킹조회_컨트롤러_테스트() throws Exception {
        // Given
        String request1 = "M";
        String request2 = "SNK";
        String request3 = "01";
        ProductRankingResponse response = createProductRankingResponse();

        given(productService.getProductRankingByType(any(), any(), any())).willReturn(List.of(response, response));

        // When & Then
        mockMvc.perform(get("/ranking")
                        .param("gender", request1)
                        .param("detail", request2)
                        .param("productType", request3)
                        .with(csrf()))
                .andExpect(status().isOk());
    }


    private ProductResponse createProductResponse() {
        return ProductResponse.builder()
                .productNumber(1L)
                .productCode("test-product-code")
                .productName("product")
                .productSubName("sub-product")
                .brandName("test-brand")
                .price(1000)
                .brandImage("/test/brand-image-url")
                .productImageResponse(createProductImageResponse())
                .createdAt(LocalDateTime.now())
                .build();
    }

    private ProductOptionResponse createProductOptionResponse() {
        return ProductOptionResponse.builder()
                .sizeAndPriceBuyInfo(Map.of(
                        "size1", 3000,
                        "size2", 2000,
                        "size3", 3000
                ))
                .sizeAndPriceSellInfo(Map.of(
                        "size1", 3000,
                        "size2", 2000,
                        "size3", 3000
                ))
                .maxSellInfo(3000)
                .minBuyInfo(2000)
                .build();
    }

    private ProductDetailResponse createProductDetailResponse() {
        return ProductDetailResponse.builder()
                .productResponse(createProductResponse())
                .productOptionResponse(createProductOptionResponse())
                .build();
    }

    private ProductImageResponse createProductImageResponse() {
        return ProductImageResponse.builder()
                .productImage(List.of("/test/url1", "/test/url2"))
                .build();
    }

    private ProductRankingResponse createProductRankingResponse() {
        return ProductRankingResponse.builder()
                .productNumber(1L)
                .productCode("test-product-code")
                .productName("product")
                .productSubName("sub-product")
                .brandName("test-brand")
                .price(1000)
                .productImageResponse(createProductImageResponse())
                .build();
    }
}