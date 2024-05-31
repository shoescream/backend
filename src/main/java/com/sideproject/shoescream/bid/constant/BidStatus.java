package com.sideproject.shoescream.bid.constant;

import lombok.Getter;

@Getter
public enum BidStatus {
    WAITING_MATCHING("waiting_matching"),
    COMPLETE_MATCHING("complete_matching"),
    CANCEL("cancel");

    private final String bidStatus;

    BidStatus(String bidStatus) {
        this.bidStatus = bidStatus;
    }
}
