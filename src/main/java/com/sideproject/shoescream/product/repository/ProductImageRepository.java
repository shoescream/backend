package com.sideproject.shoescream.product.repository;

import com.sideproject.shoescream.product.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
}
