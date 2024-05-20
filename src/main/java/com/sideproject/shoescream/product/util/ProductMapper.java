package com.sideproject.shoescream.product.util;

import com.sideproject.shoescream.product.dto.response.*;
import com.sideproject.shoescream.product.entity.Product;
import com.sideproject.shoescream.product.entity.ProductImage;
import com.sideproject.shoescream.product.entity.ProductOption;

import java.util.List;
import java.util.Map;
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

    public static ProductOptionResponse toProductOptionResponse(List<ProductOption> productOption) {
        Map<String, Integer> sizeAndPriceBuyInfo = productOption.stream()
                .collect(Collectors.toMap(ProductOption::getSize, ProductOption::getHighestPrice));

        Map<String, Integer> sizeAndPriceSellInfo = productOption.stream()
                .collect(Collectors.toMap(ProductOption::getSize, ProductOption::getLowestPrice));

        return ProductOptionResponse.builder()
                .sizeAndPriceBuyInfo(sizeAndPriceSellInfo)
                .sizeAndPriceSellInfo(sizeAndPriceBuyInfo)
                .minBuyInfo(findMinSellPrice(sizeAndPriceSellInfo))
                .maxSellInfo(findMaxBuyPrice(sizeAndPriceBuyInfo))
                .build();
    }

    public static ProductDetailResponse toProductDetailResponse(Product product) {
        List<ProductOption> productOption = product.getProductOption().stream()
                .toList();
        return ProductDetailResponse.builder()
                .productResponse(toProductResponse(product))
                .productOptionResponse(toProductOptionResponse(productOption))
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

    private static Integer findMinSellPrice(Map<String, Integer> sizeAndPriceSellInfo) {
        if (sizeAndPriceSellInfo == null || sizeAndPriceSellInfo.isEmpty()) {
            return 0;
        }
        return sizeAndPriceSellInfo.values().stream().min(Integer::compareTo).get();
    }

    private static Integer findMaxBuyPrice(Map<String, Integer> sizeAndPriceBuyInfo) {
        if (sizeAndPriceBuyInfo == null || sizeAndPriceBuyInfo.isEmpty()) {
            return 0;
        }
        return sizeAndPriceBuyInfo.values().stream().max(Integer::compareTo).get();
    }
}
