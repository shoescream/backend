package com.sideproject.shoescream.product.util;

import com.sideproject.shoescream.product.dto.response.ProductDetailResponse;
import com.sideproject.shoescream.product.dto.response.ProductOptionResponse;
import com.sideproject.shoescream.product.dto.response.ProductResponse;
import com.sideproject.shoescream.product.entity.Product;
import com.sideproject.shoescream.product.entity.ProductOption;

public class ProductMapper {

    public static ProductResponse toProductResponse(Product product) {
        return ProductResponse.builder()
                .productName(product.getProductName())
                .productSubName(product.getProductSubName())
                .brandName(product.getBrandName())
                .productImage(product.getProductImage())
                .createdAt(product.getCreatedAt())
                .views(product.getViews())
                .build();
    }

    public static ProductOptionResponse toProductOptionResponse(ProductOption productOption) {
        return ProductOptionResponse.builder()
                .size(productOption.getSize())
                .build();
    }

    public static ProductDetailResponse toProductDetailResponse(Product product) {
        return ProductDetailResponse.builder()
                .productResponse(toProductResponse(product))
                .productOptionResponse(toProductOptionResponse(product.getProductOption()))
                .build();
    }
}
