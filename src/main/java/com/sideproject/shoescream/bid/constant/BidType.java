package com.sideproject.shoescream.bid.constant;

import lombok.Getter;

@Getter
public enum BidType {

    SELL_BID("sell_bid"),
    SELL_NOW("sell_now"),
    BUY_BID("buy_bid"),
    BUY_NOW("buy_now");

    private final String bidType;

    BidType(String bidType) {
        this.bidType = bidType;
    }

    }
