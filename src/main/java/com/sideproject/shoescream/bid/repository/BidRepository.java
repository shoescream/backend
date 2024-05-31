package com.sideproject.shoescream.bid.repository;

import com.sideproject.shoescream.bid.constant.BidType;
import com.sideproject.shoescream.bid.entity.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BidRepository extends JpaRepository<Bid, Long> {

    @Query("select b from Bid b where b.member.memberNumber = :memberNumber")
    List<Bid> findByMemberNumber(@Param("memberNumber") Long memberNumber);

    @Query("select b from Bid b where b.product.id =:productNumber and b.bidPrice =:bidPrice and b.bidType =:bidType order by b.createdAt")
    Optional<Bid> findTargetBidOne(@Param("productNumber") Long productNumber,
                                @Param("bidPrice") int bidPrice,
                                @Param("bidType") BidType bidType);
}
