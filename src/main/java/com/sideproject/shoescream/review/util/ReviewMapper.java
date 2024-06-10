package com.sideproject.shoescream.review.util;

import com.sideproject.shoescream.member.entity.Member;
import com.sideproject.shoescream.product.entity.Product;
import com.sideproject.shoescream.review.dto.request.ReviewCommentPostRequest;
import com.sideproject.shoescream.review.dto.request.ReviewPostRequest;
import com.sideproject.shoescream.review.dto.response.ReviewResponse;
import com.sideproject.shoescream.review.dto.response.ReviewCommentResponse;
import com.sideproject.shoescream.review.entity.Review;
import com.sideproject.shoescream.review.entity.ReviewComment;
import com.sideproject.shoescream.review.entity.ReviewImage;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ReviewMapper {

    public static Review toReview(ReviewPostRequest reviewPostRequest, Member member, Product product) {
        LocalDateTime now = LocalDateTime.now();
        // reviewImage는 List<ReviewImage> 타입으로 바꿔줘서 저장해야함
        return Review.builder()
                .member(member)
                .product(product)
                .rating(reviewPostRequest.rating())
                .reviewTitle(reviewPostRequest.reviewTitle())
                .reviewContent(reviewPostRequest.reviewContent())
                .createdAt(now)
                .build();
    }

    public static ReviewResponse toReviewResponse(Review review, List<String> reviewImages) {
        return ReviewResponse.builder()
                .reviewNumber(review.getReviewNumber())
                .memberId(review.getMember().getMemberId())
                .rating(review.getRating())
                .createdAt(review.getCreatedAt())
                .reviewTitle(review.getReviewTitle())
                .reviewContent(review.getReviewContent())
                .reviewImages(reviewImages)
                .build();
    }

    public static ReviewResponse toUpdateReviewResponse(Review review) {
        return ReviewResponse.builder()
                .reviewNumber(review.getReviewNumber())
                .memberId(review.getMember().getMemberId())
                .rating(review.getRating())
                .createdAt(review.getCreatedAt())
                .reviewTitle(review.getReviewTitle())
                .reviewContent(review.getReviewContent())
                .build();
    }

    public static ReviewComment toReviewComment(ReviewCommentPostRequest reviewCommentPostRequest, Member member, Review review) {
        LocalDateTime now = LocalDateTime.now();
        return ReviewComment.builder()
                .member(member)
                .review(review)
                .commentContent(reviewCommentPostRequest.commentContent())
                .createdAt(now)
                .build();
    }

    // ReviewComment 엔티티를 ReviewCommentResponse DTO로 변환
    public static ReviewCommentResponse toReviewCommentResponse(ReviewComment reviewComment) {
        return ReviewCommentResponse.builder()
                .commentNumber(reviewComment.getCommentNumber())
                .commentContent(reviewComment.getCommentContent())
                .createdAt(reviewComment.getCreatedAt())
                .memberId(reviewComment.getMember().getMemberId())
                .build();

    }

    public static ReviewCommentResponse toUpdateReviewCommentResponse(ReviewComment reviewComment) {
        return ReviewCommentResponse.builder()
                .commentNumber(reviewComment.getCommentNumber())
                .commentContent(reviewComment.getCommentContent())
                .createdAt(reviewComment.getCreatedAt())
                .memberId(reviewComment.getMember().getMemberId())
                .build();
    }
}
