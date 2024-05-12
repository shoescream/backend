package com.sideproject.shoescream.product.util;

import com.sideproject.shoescream.product.dto.response.ProductResponse;
import com.sideproject.shoescream.product.entity.Product;

public class ProductMapper {

    public static ProductResponse toProductResponse(Product product) {
        return ProductResponse.builder()
                .product(product)
                .build();
    }
}
