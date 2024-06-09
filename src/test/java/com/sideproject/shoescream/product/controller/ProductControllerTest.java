package com.sideproject.shoescream.product.controller;

import com.sideproject.shoescream.product.dto.response.ProductResponse;
import com.sideproject.shoescream.product.entity.Product;
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

import static org.mockito.BDDMockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.junit.jupiter.api.Assertions.*;
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
        ProductResponse response = createProductResponse();

        given(productService.getProduct(any())).willReturn(response);

        mockMvc.perform(get("/products/{productNumber}", request)
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    private ProductResponse createProductResponse() {
        return ProductResponse.builder()
                .product(createProduct())
                .build();
    }

    private Product createProduct() {
        return Product.builder()
                .productName("Nike Shoes")
                .productSubName("나이키 신발")
                .productImage("/test/url")
                .brandName("Nike")
                .createdAt(LocalDateTime.now())
                .views(0L)
                .build();
    }
}