package com.sideproject.shoescream.member.util;

import com.sideproject.shoescream.bid.entity.Bid;
import com.sideproject.shoescream.bid.entity.Deal;
import com.sideproject.shoescream.member.dto.request.MemberSignUpRequest;
import com.sideproject.shoescream.member.dto.response.*;
import com.sideproject.shoescream.member.entity.Member;
import com.sideproject.shoescream.notification.entity.Notification;

public class MemberMapper {

    public static Member toMember(MemberSignUpRequest memberSignUpRequest, String encodePassword) {
        return Member.builder()
                .memberId(memberSignUpRequest.memberId())
                .password(encodePassword)
                .email(memberSignUpRequest.email())
                .name(memberSignUpRequest.name())
                .build();
    }

    public static MemberResponse toMemberResponse(Member member) {
        return MemberResponse.builder()
                .memberNumber(member.getMemberNumber())
                .memberId(member.getMemberId())
                .email(member.getEmail())
                .name(member.getName())
                .profileImage(member.getProfileImage())
                .build();
    }

    public static TokenResponse toTokenResponse(String accessToken, String refreshToken) {
        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public static MemberSignInResponse toSignInResponse(MemberResponse memberResponse, TokenResponse tokenResponse) {
        return MemberSignInResponse.builder()
                .memberResponse(memberResponse)
                .tokenResponse(tokenResponse)
                .build();
    }

    public static MyBuyingHistoryResponse toMyBuyingBidHistoryResponse(Bid myBid) {
        return MyBuyingHistoryResponse.builder()
                .productName(myBid.getProduct().getProductName())
                .productImage(myBid.getProduct().getProductImages().get(0).getProductImage())
                .price(myBid.getBidPrice())
                .size(myBid.getSize())
                .createdAt(myBid.getCreatedAt())
                .deadLine(myBid.getBidDeadLine())
                .type(myBid.getBidType().getBidType())
                .status(myBid.getBidStatus().getBidStatus())
                .build();
    }

    public static MyBuyingHistoryResponse toMyBuyingPendingDealHistoryResponse(Deal myDeal) {
        return MyBuyingHistoryResponse.builder()
                .productName(myDeal.getProduct().getProductName())
                .productImage(myDeal.getProduct().getProductImages().get(0).getProductImage())
                .price(myDeal.getPrice())
                .size(myDeal.getSize())
                .createdAt(myDeal.getCreatedAt())
                .status(myDeal.getDealStatus().getDealStatus())
                .build();
    }

    public static MyBuyingHistoryResponse toMyBuyingFinishedDealHistoryResponse(Deal myDeal) {
        return MyBuyingHistoryResponse.builder()
                .productName(myDeal.getProduct().getProductName())
                .productImage(myDeal.getProduct().getProductImages().get(0).getProductImage())
                .price(myDeal.getPrice())
                .size(myDeal.getSize())
                .tradedAt(myDeal.getTradedAt())
                .status(myDeal.getDealStatus().getDealStatus())
                .isWriteReview(myDeal.getIsWriteReview())
                .build();
    }

    public static MySellingHistoryResponse toMySellingBidHistoryResponse(Bid myBid) {
        return MySellingHistoryResponse.builder()
                .productName(myBid.getProduct().getProductName())
                .productImage(myBid.getProduct().getProductImages().get(0).getProductImage())
                .price(myBid.getBidPrice())
                .size(myBid.getSize())
                .createdAt(myBid.getCreatedAt())
                .deadLine(myBid.getBidDeadLine())
                .type(myBid.getBidType().getBidType())
                .status(myBid.getBidStatus().getBidStatus())
                .build();
    }

    public static MySellingHistoryResponse toMySellingPendingDealHistoryResponse(Deal myDeal) {
        return MySellingHistoryResponse.builder()
                .productName(myDeal.getProduct().getProductName())
                .productImage(myDeal.getProduct().getProductImages().get(0).getProductImage())
                .price(myDeal.getPrice())
                .size(myDeal.getSize())
                .createdAt(myDeal.getCreatedAt())
                .status(myDeal.getDealStatus().getDealStatus())
                .build();
    }

    public static MySellingHistoryResponse toMySellingFinishedDealHistoryResponse(Deal myDeal) {
        return MySellingHistoryResponse.builder()
                .productName(myDeal.getProduct().getProductName())
                .productImage(myDeal.getProduct().getProductImages().get(0).getProductImage())
                .price(myDeal.getPrice())
                .size(myDeal.getSize())
                .tradedAt(myDeal.getTradedAt())
                .status(myDeal.getDealStatus().getDealStatus())
                .build();
    }

    public static MyWritableReviewResponse toMyWritableReviewResponse(Deal deal) {
        return MyWritableReviewResponse.builder()
                .dealNumber(deal.getDealNumber())
                .productNumber(deal.getProduct().getProductNumber())
                .productName(deal.getProduct().getProductName())
                .productSubName(deal.getProduct().getProductSubName())
                .productImage(deal.getProduct().getProductImages().get(0).getProductImage())
                .dealSize(deal.getSize())
                .dealPrice(deal.getPrice())
                .writeDeadLine(deal.getTradedAt().plusWeeks(2))
                .isWriteReview(deal.getIsWriteReview())
                .build();
    }

    public static MyNotificationResponse toMyNotificationResponse(Notification notification) {
        return MyNotificationResponse.builder()
                .notificationNumber(notification.getNotificationNumber())
                .notificationContent(notification.getNotificationContent())
                .notificationType(notification.getNotificationType())
                .buyerId(notification.getReceiver().getMemberId())
                .createdAt(notification.getCreatedAt())
                .build();
    }

    public static MyNotificationResponse toMyNotificationResponse(Notification notification, Object object){
        return MyNotificationResponse.builder()
                .notificationNumber(notification.getNotificationNumber())
                .notificationContent(notification.getNotificationContent())
                .notificationType(notification.getNotificationType())
                .buyerId(notification.getReceiver().getMemberId())
                .createdAt(notification.getCreatedAt())
                .object(object)
                .build();
    }
}
