package com.sideproject.shoescream.product.controller;

import com.sideproject.shoescream.global.dto.response.Response;
import com.sideproject.shoescream.product.dto.response.ProductDetailResponse;
import com.sideproject.shoescream.product.dto.response.ProductRankingResponse;
import com.sideproject.shoescream.product.dto.response.ProductResponse;
import com.sideproject.shoescream.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/products")
    public Response<List<ProductResponse>> getAllProducts() {
        return Response.success(productService.getAllProducts());
    }

    @GetMapping("/products/{productNumber}")
    public Response<ProductDetailResponse> getProduct(@PathVariable String productNumber) {
        return Response.success(productService.getProduct(productNumber));
    }

    @GetMapping("/ranking")
    public Response<List<ProductRankingResponse>> getProductRankingByType(@RequestParam String gender, String detail, String productType) {
        return Response.success(productService.getProductRankingByType(gender, detail, productType));
    }
}
