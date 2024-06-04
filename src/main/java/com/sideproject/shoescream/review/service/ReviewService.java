package com.sideproject.shoescream.review.service;

import com.sideproject.shoescream.global.exception.ErrorCode;
import com.sideproject.shoescream.member.entity.Member;
import com.sideproject.shoescream.member.exception.MemberNotFoundException;
import com.sideproject.shoescream.member.repository.MemberRepository;
import com.sideproject.shoescream.product.entity.Product;
import com.sideproject.shoescream.product.repository.ProductRepository;
import com.sideproject.shoescream.review.dto.request.ReviewPostRequest;
import com.sideproject.shoescream.review.dto.response.ReviewResponse;
import com.sideproject.shoescream.review.entity.Review;
import com.sideproject.shoescream.review.repository.ReviewRepository;
import com.sideproject.shoescream.review.util.ReviewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private ReviewRepository reviewRepository;
    private MemberRepository memberRepository;
    private ProductRepository productRepository;

    public List<ReviewResponse> getAllReviewsByProductNumber(Long productNumber) {
        List<Review> reviews = reviewRepository.findByProductNumber(productNumber);
        return reviews.stream()
                .map(ReviewResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public ReviewResponse getReviewById(Long reviewNumber) {
        Review review = reviewRepository.findById(reviewNumber)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        return ReviewResponse.fromEntity(review);
    }

    @Transactional
    public ReviewResponse postReview(ReviewPostRequest reviewPostRequest, Long productNumber, String memberId) {
        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
        Product product = productRepository.findById(productNumber)
                .orElseThrow(() -> new RuntimeException("no product"));

        return ReviewMapper.toReviewResponse(reviewRepository.save(
                ReviewMapper.toReview(reviewPostRequest, member, product)));
    }
}
