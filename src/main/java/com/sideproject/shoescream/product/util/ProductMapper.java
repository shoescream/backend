package com.sideproject.shoescream.product.util;

import com.sideproject.shoescream.product.dto.response.*;
import com.sideproject.shoescream.product.entity.Product;
import com.sideproject.shoescream.product.entity.ProductImage;
import com.sideproject.shoescream.product.entity.ProductOption;

import java.util.List;
import java.util.stream.Collectors;

public class ProductMapper {

    public static ProductResponse toProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .productCode(product.getProductCode())
                .productName(product.getProductName())
                .productSubName(product.getProductSubName())
                .brandName(product.getBrandName())
                .price(product.getPrice())
                .brandImage(product.getBrandImage())
                .productImageResponse(toProductImageResponse(product))
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

    public static ProductRankingResponse toProductRankingResponse(Product product) {
        return ProductRankingResponse.builder()
                .id(product.getId())
                .productCode(product.getProductCode())
                .productName(product.getProductName())
                .productSubName(product.getProductSubName())
                .productImageResponse(toProductImageResponse(product))
                .build();
    }

    public static ProductImageResponse toProductImageResponse(Product product) {
        List<String> imageUrls = product.getImages().stream()
                .map(ProductImage::getProductImage)
                .toList();

        return ProductImageResponse.builder()
                .productImage(imageUrls)
                .build();

    }
}
