package com.sideproject.shoescream.review.dto.request;

public record ReviewUpdateRequest(
        String reviewTitle,
        String reviewContent,
        int rating
) {
}
