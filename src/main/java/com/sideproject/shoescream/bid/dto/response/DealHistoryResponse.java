package com.sideproject.shoescream.bid.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record DealHistoryResponse(
        List<DealResponse> dealResponse
) {
}
