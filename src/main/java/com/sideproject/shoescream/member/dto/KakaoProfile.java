package com.sideproject.shoescream.member.dto;

import lombok.Data;

@Data
public class KakaoProfile {
    public Long id;
    public String connected_at;
    public KakaoAccount kakaoAccount;

    @Data
    public class KakaoAccount {
        public String nickname;
        public String email;
    }
}
