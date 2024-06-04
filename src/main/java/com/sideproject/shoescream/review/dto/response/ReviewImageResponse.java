package com.sideproject.shoescream.review.dto.response;

import lombok.Builder;
import lombok.Getter;
import com.sideproject.shoescream.review.entity.ReviewImage;

@Getter
@Builder
public class ReviewImageResponse {
    private String reviewImageUrl;

    public static ReviewImageResponse fromEntity(ReviewImage reviewImage) {
        return ReviewImageResponse.builder()
                .reviewImageUrl(reviewImage.getReviewImageUrl())
                .build();
    }
}
