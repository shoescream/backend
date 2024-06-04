package com.sideproject.shoescream.review.repository;

import com.sideproject.shoescream.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query(value = "select r from Review r where r.product.productNumber= :productNumber order by r.createdAt desc ")
    List<Review> findByProductNumber(@Param("productNumber") Long productNumber);
}
