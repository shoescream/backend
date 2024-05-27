package com.sideproject.shoescream.bid.repository;

import com.sideproject.shoescream.bid.entity.Bid;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BidRepository extends JpaRepository<Bid, Long> {
}
