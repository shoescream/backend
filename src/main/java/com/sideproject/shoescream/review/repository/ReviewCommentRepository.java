package com.sideproject.shoescream.review.repository;

import com.sideproject.shoescream.review.entity.Review;
import com.sideproject.shoescream.review.entity.ReviewComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewCommentRepository extends JpaRepository<ReviewComment, Long> {

    @Query(value = "select r from ReviewComment r where r.review.reviewNumber= :reviewNumber order by r.createdAt desc ")
    List<ReviewComment> findByCommentsForReviewNumber(@Param("reviewNumber") Long reviewNumber);
}
