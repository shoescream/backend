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
import java.util.Objects;
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

    public List<ReviewResponse> getAllReviewsByProductNumber(Long productNumber) {
        List<Review> reviews = reviewRepository.findByProductNumber(productNumber);
        return reviews.stream()
                .map(ReviewResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public List<ReviewResponse> getRecentReviewsByProductNumber(Long productNumber) {
        List<Review> recentReviews = reviewRepository.findTop8ByProductProductNumberOrderByCreatedAtDesc(productNumber);
        return recentReviews.stream()
                .map(ReviewResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public ReviewResponse getReviewById(Long reviewNumber) {
        Review review = reviewRepository.findById(reviewNumber)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        return ReviewResponse.fromEntity(review);
    }

    public List<ReviewCommentResponse> getAllReviewCommentsByReviewNumber(Long reviewNumber) {
        List<ReviewComment> reviewComments = reviewCommentRepository.findByCommentsForReviewNumber(reviewNumber);
        return reviewComments.stream()
                .map(ReviewCommentResponse::fromEntity)
                .collect(Collectors.toList());
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
                .orElseThrow(() -> new RuntimeException());

        if (!Objects.equals(member.getMemberNumber(), review.getMember().getMemberNumber())) {
            throw new RuntimeException();
        }
        review.setReviewTitle(reviewUpdateRequest.reviewTitle());
        review.setReviewContent(reviewUpdateRequest.reviewContent());
        review.setRating(reviewUpdateRequest.rating());

        return ReviewMapper.toUpdateReviewResponse(review);
    }

    @Transactional
    public String deleteReview(Long reviewNumber, String memberId) {
        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
        Review review = reviewRepository.findById(reviewNumber)
                .orElseThrow(() -> new RuntimeException("리뷰가 없습니다."));

        if (!Objects.equals(member.getMemberNumber(), review.getMember().getMemberNumber())) {
            throw new RuntimeException();
        }

        reviewRepository.deleteById(reviewNumber);

        return "삭제 완료";
    }

    @Transactional
    public ReviewCommentResponse postReviewComment(ReviewCommentPostRequest reviewCommentPostRequest, Long reviewNumber, String memberId) {
        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
        Review review = reviewRepository.findById(reviewNumber)
                .orElseThrow(() -> new RuntimeException());
        ReviewComment reviewComment = reviewCommentRepository.save(
                ReviewMapper.toReviewComment(reviewCommentPostRequest, member, review));
        return ReviewMapper.toReviewCommentResponse(reviewComment);
    }

    @Transactional
    public ReviewCommentResponse updateReviewComment(ReviewCommentPostRequest reviewCommentPostRequest, Long commentNumber, String memberId) {
        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
        Review review = reviewRepository.findById(reviewCommentPostRequest.reviewNumber())
                .orElseThrow(() -> new RuntimeException());
        //TODO: 업데이트 시간 변경 요망

        ReviewComment reviewComment = reviewCommentRepository.findById(commentNumber)
                .orElseThrow(() -> new RuntimeException());
        reviewComment.setCommentContent(reviewCommentPostRequest.commentContent());

        return ReviewMapper.toUpdateReviewCommentResponse(reviewComment);
    }

    @Transactional
    public String deleteReviewComment(Long commentNumber, String memberId) {
        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
        ReviewComment reviewComment = reviewCommentRepository.findById(commentNumber)
                .orElseThrow(() -> new RuntimeException("리뷰 댓글 없습니다."));

        if (!Objects.equals(member.getMemberNumber(), reviewComment.getMember().getMemberNumber())) {
            throw new RuntimeException();
        }

        reviewCommentRepository.deleteById(commentNumber);
        return "삭제 완료";
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
