package com.sideproject.shoescream.bid.repository;

import com.sideproject.shoescream.bid.entity.Deal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DealRepository extends JpaRepository<Deal, Long> {

    List<Deal> findByProduct_ProductNumber(Long productNumber);

    @Query("select d from Deal d where d.buyerNumber=:memberNumber or d.sellerNumber=:memberNumber")
    List<Deal> findByMemberNumber(@Param("memberNumber") Long memberNumber);
}
