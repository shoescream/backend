package com.sideproject.shoescream.bid.constant;

import lombok.Getter;

@Getter
public enum BidType {

    SELL_BID("sellBid"),
    BUY_BID("buyBid");

    private final String bidType;

    BidType(String bidType) {
        this.bidType = bidType;
    }

    }
