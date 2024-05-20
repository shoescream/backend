package com.sideproject.shoescream.product.repository;

import com.sideproject.shoescream.product.entity.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {
    ProductOption findByProductIdAndSize(Long productNumber, String size);
}
