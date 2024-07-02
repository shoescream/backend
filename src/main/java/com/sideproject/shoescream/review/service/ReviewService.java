package com.sideproject.shoescream.review.service;

import com.sideproject.shoescream.bid.entity.Deal;
import com.sideproject.shoescream.bid.exception.DealNotFoundException;
import com.sideproject.shoescream.bid.repository.DealRepository;
import com.sideproject.shoescream.global.exception.ErrorCode;
import com.sideproject.shoescream.global.service.S3Service;
import com.sideproject.shoescream.member.entity.Member;
import com.sideproject.shoescream.member.exception.MemberNotFoundException;
import com.sideproject.shoescream.member.repository.MemberRepository;
import com.sideproject.shoescream.product.entity.Product;
import com.sideproject.shoescream.product.exception.ProductNotFoundException;
import com.sideproject.shoescream.product.repository.ProductRepository;
import com.sideproject.shoescream.review.dto.request.ReviewCommentPostRequest;
import com.sideproject.shoescream.review.dto.request.ReviewPostRequest;
import com.sideproject.shoescream.review.dto.request.ReviewUpdateRequest;
import com.sideproject.shoescream.review.dto.response.ReviewCommentResponse;
import com.sideproject.shoescream.review.dto.response.ReviewResponse;
import com.sideproject.shoescream.review.entity.Review;
import com.sideproject.shoescream.review.entity.ReviewComment;
import com.sideproject.shoescream.review.entity.ReviewImage;
import com.sideproject.shoescream.review.exception.ReviewCommentNotFoundException;
import com.sideproject.shoescream.review.exception.ReviewNotFoundException;
import com.sideproject.shoescream.review.repository.ReviewCommentRepository;
import com.sideproject.shoescream.review.repository.ReviewImageRepository;
import com.sideproject.shoescream.review.repository.ReviewRepository;
import com.sideproject.shoescream.review.util.ReviewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final S3Service s3Service;
    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final ReviewImageRepository reviewImageRepository;
    private final ReviewCommentRepository reviewCommentRepository;
    private final DealRepository dealRepository;

    @Transactional(readOnly = true)
    public List<ReviewResponse> getAllReviewsByProductNumber(Long productNumber) {
        List<Review> reviews = reviewRepository.findByProductNumber(productNumber);
        return reviews.stream()
                .map(ReviewResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ReviewResponse> getRecentReviewsByProductNumber(Long productNumber) {
        List<Review> recentReviews = reviewRepository.findTop8ByProductProductNumberOrderByCreatedAtDesc(productNumber);
        return recentReviews.stream()
                .map(ReviewResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ReviewResponse getReviewById(Long reviewNumber) {
        Review review = reviewRepository.findById(reviewNumber)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        return ReviewResponse.fromEntity(review);
    }

    @Transactional
    public ReviewResponse postReview(ReviewPostRequest reviewPostRequest, List<MultipartFile> reviewImages, Long productNumber, String memberId) {
        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
        Product product = productRepository.findById(productNumber)
                .orElseThrow(() -> new ProductNotFoundException(ErrorCode.PRODUCT_NOT_FOUND));
        Deal deal = dealRepository.findById(reviewPostRequest.dealNumber())
                .orElseThrow(() -> new DealNotFoundException(ErrorCode.DEAL_NOT_FOUND));

        Review review = reviewRepository.save(
                ReviewMapper.toReview(reviewPostRequest, member, product));
        List<String> reviewImagesResult = saveReviewImages(s3Service.upload(reviewImages), review);

        deal.setIsWriteReview(true);

        return ReviewMapper.toReviewResponse(
                review, reviewImagesResult);
    }

    @Transactional
    public ReviewResponse updateReview(ReviewUpdateRequest reviewUpdateRequest, Long reviewNumber, String memberId) {
        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
        Review review = reviewRepository.findById(reviewNumber)
                .orElseThrow(() -> new ReviewNotFoundException(ErrorCode.REVIEW_NOT_FOUND));

        review.validateReviewAccessRight(member.getMemberNumber());
        review.updateReview(reviewUpdateRequest.reviewTitle(), reviewUpdateRequest.reviewContent(), reviewUpdateRequest.rating());

        return ReviewMapper.toUpdateReviewResponse(review);
    }

    @Transactional
    public void deleteReview(Long reviewNumber, String memberId) {
        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
        Review review = reviewRepository.findById(reviewNumber)
                .orElseThrow(() -> new ReviewNotFoundException(ErrorCode.REVIEW_NOT_FOUND));

        review.validateReviewAccessRight(member.getMemberNumber());
        reviewRepository.deleteById(reviewNumber);
    }

    @Transactional
    public ReviewCommentResponse postReviewComment(ReviewCommentPostRequest reviewCommentPostRequest, Long reviewNumber, String memberId) {
        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
        Review review = reviewRepository.findById(reviewNumber)
                .orElseThrow(() -> new ReviewNotFoundException(ErrorCode.REVIEW_NOT_FOUND));
        ReviewComment reviewComment = reviewCommentRepository.save(
                ReviewMapper.toReviewComment(reviewCommentPostRequest, member, review));
        return ReviewMapper.toReviewCommentResponse(reviewComment);
    }

    @Transactional
    public ReviewCommentResponse updateReviewComment(ReviewCommentPostRequest reviewCommentPostRequest, Long commentNumber, String memberId) {
        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
        ReviewComment reviewComment = reviewCommentRepository.findById(commentNumber)
                .orElseThrow(() -> new ReviewCommentNotFoundException(ErrorCode.REVIEW_COMMENT_NOT_FOUND));

        reviewComment.validateReviewCommentAccessRight(member.getMemberNumber());
        reviewComment.updateReview(reviewCommentPostRequest.commentContent());

        return ReviewMapper.toUpdateReviewCommentResponse(reviewComment);
    }

    @Transactional
    public void deleteReviewComment(Long commentNumber, String memberId) {
        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
        ReviewComment reviewComment = reviewCommentRepository.findById(commentNumber)
                .orElseThrow(() -> new ReviewCommentNotFoundException(ErrorCode.REVIEW_COMMENT_NOT_FOUND));

        reviewComment.validateReviewCommentAccessRight(member.getMemberNumber());
        reviewCommentRepository.deleteById(commentNumber);
    }

    private List<String> saveReviewImages(List<String> reviewImagesInS3Bucket, Review savedReview) {
        List<String> reviewImages = new ArrayList<>();

        for (String imgUrl : reviewImagesInS3Bucket) {
            ReviewImage reviewImage = ReviewImage.builder()
                    .reviewImageUrl(imgUrl)
                    .review(savedReview)
                    .build();
            reviewImageRepository.save(reviewImage);
            reviewImages.add(reviewImage.getReviewImageUrl());
        }
        return reviewImages;
    }
}
