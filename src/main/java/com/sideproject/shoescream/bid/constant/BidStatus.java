package com.sideproject.shoescream.bid.constant;

import lombok.Getter;

@Getter
public enum BidStatus {
    WAITING_MATCHING("waitingMating"),
    COMPLETE_MATCHING("completeMating"),
    NONE("none"),
    CANCEL("cancle");

    private final String bidStatus;

    BidStatus(String bidStatus) {
        this.bidStatus = bidStatus;
    }
}
