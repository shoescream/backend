package com.sideproject.shoescream.review.repository;

import com.sideproject.shoescream.review.entity.ReviewComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<ReviewComment, Long> {
}
