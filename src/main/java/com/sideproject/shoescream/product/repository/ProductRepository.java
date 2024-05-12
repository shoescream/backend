package com.sideproject.shoescream.product.repository;

import com.sideproject.shoescream.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("select p from Product p order by p.createdAt asc ")
    List<Product> findAllOrderByCreatedAtAsc();
}
