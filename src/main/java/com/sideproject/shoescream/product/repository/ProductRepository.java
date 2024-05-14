package com.sideproject.shoescream.product.repository;

import com.sideproject.shoescream.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("select p from Product p order by p.createdAt asc ")
    List<Product> findAllOrderByCreatedAtAsc();

    @Query(value = "select * from product p where p.product_code like concat(:gender, '-', :detail, '-', :productType, '%') order by p.views desc limit 30", nativeQuery = true)
    List<Product> findTop30ByGenderAndCategoryAndProductTypeOrderByViewsDesc(@Param("gender") String gender, @Param("detail") String detail, @Param("productType") String productType);
}
