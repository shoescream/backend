package com.sideproject.shoescream.review.dto.request;

import lombok.Builder;

@Builder
public record ReviewPostRequest(
        String reviewTitle,
        String reviewContent,
        int rating,
        long dealNumber
) {
}
