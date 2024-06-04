package com.sideproject.shoescream.pay.dto;

import lombok.Builder;

@Builder
public record KakaoPayInfoRequest(
    String item_name,
    int quantity,
    int total_amount

) {
}
