package com.sideproject.shoescream.bid.repository;

import com.sideproject.shoescream.bid.entity.Deal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DealRepository extends JpaRepository<Deal, Long> {

    List<Deal> findByProductId(Long productNumber);
}
