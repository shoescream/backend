package com.sideproject.shoescream.pay.dto;

import lombok.Builder;

@Builder
public record KakaoReadyResponse(
        String tid,
        String next_redirect_pc_url,
        String created_At
) {
}
