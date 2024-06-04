package com.sideproject.shoescream.review.dto.request;

import lombok.Builder;

import java.util.List;

@Builder
public record ReviewPostRequest(
        String reviewTitle,
        String reviewContent,
        int rating
) {
}
