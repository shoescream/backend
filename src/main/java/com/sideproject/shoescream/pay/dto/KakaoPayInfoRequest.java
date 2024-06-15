package com.sideproject.shoescream.pay.dto;

import lombok.Builder;

@Builder
public record KakaoPayInfoRequest(
    long bid_number,
    String item_name,
    int quantity,
    int total_amount

) {
}
