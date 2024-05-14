package com.sideproject.shoescream.product.dto.response;

import com.sideproject.shoescream.product.constant.SizeType;
import lombok.Builder;

@Builder
public record ProductOptionResponse(
        SizeType size) {
}
