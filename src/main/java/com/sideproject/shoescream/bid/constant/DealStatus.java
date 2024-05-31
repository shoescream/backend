package com.sideproject.shoescream.bid.constant;

import lombok.Getter;

@Getter
public enum DealStatus {
    WAITING_DEPOSIT("waiting_deposit"),
    COMPLETE_DEPOSIT("complete_deposit"),
    FAIL_DEAL("fail_deal"),
    SUCCESS_DEAL("success_deal");

    private final String dealStatus;

    DealStatus(String dealStatus) {
        this.dealStatus = dealStatus;
    }
}
