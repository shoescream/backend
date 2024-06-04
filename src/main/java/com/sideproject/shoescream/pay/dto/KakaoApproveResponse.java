package com.sideproject.shoescream.pay.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
public record KakaoApproveResponse(
         String aid, // 요청 고유 번호
         String tid, // 결제 고유 번호
         String cid, // 가맹점 코드
         String sid,// 정기결제용 ID
         String partner_order_id, // 가맹점 주문 번호
         String partner_user_id, // 가맹점 회원 id
         String payment_method_type, // 결제 수단
         Amount amount, // 결제 금액 정보
         String item_name, // 상품명
         String item_code, // 상품 코드
         int quantity, // 상품 수량
         String created_at, // 결제 요청 시간
         String approved_at, // 결제 승인 시간
         String payload // 결제 승인 요청에 대해 저장 값, 요청 시 전달 내용
) {

    @Getter
    @Setter
    public static class Amount {
        private int total; // 총 결제 금액
        private int tax_free; // 비과세 금액
        private int tax; // 부가세 금액
        private int point; // 사용한 포인트
        private int discount; // 할인금액
        private int green_deposit; // 컵 보증금
    }
}
