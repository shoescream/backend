package com.sideproject.shoescream.bid.constant;

import lombok.Getter;

@Getter
public enum DealStatus {
    WAITING_DEPOSIT("pending"),
    COMPLETE_DEPOSIT("pending"),
    WAITING_TRANSFER("pending"),
    COMPLETE_TRANSFER("pending"),
    FAIL_DEAL("finishing"),
    SUCCESS_DEAL("finishing");

    private final String dealStatus;

    DealStatus(String dealStatus) {
        this.dealStatus = dealStatus;
    }
}
