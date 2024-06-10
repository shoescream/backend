package com.sideproject.shoescream.review.dto.request;

import lombok.Builder;

@Builder
public record ReviewCommentPostRequest(
        Long reviewNumber,
        String commentContent
) {
}