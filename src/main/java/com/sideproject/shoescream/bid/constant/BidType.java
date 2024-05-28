package com.sideproject.shoescream.bid.constant;

import lombok.Getter;

@Getter
public enum BidType {
    SELL_BID("sellBid"),
    SELL_NOW("sellNow"),
    BUY_BID("buyBid"),
    BUY_NOW("buyNow");

    private final String bidType;

    BidType(String bidType) {
        this.bidType = bidType;
    }

}
