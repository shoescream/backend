package com.sideproject.shoescream.bid.controller;

import com.sideproject.shoescream.bid.dto.request.BuyingBidRequest;
import com.sideproject.shoescream.bid.dto.request.SellingBidRequest;
import com.sideproject.shoescream.bid.dto.response.*;
import com.sideproject.shoescream.bid.service.BidService;
import com.sideproject.shoescream.global.dto.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BidController {

    private final BidService bidService;

    @GetMapping("/sell/{productNumber}")
    public Response<SellingProductInfoResponse> getSellingProductInfo(@PathVariable Long productNumber, @RequestParam String size) {
        return Response.success(bidService.getSellingProductInfo(productNumber, size));
    }

    @PostMapping("/sell-bid")
    public Response<SellingBidResponse> sellingBid(@RequestBody SellingBidRequest sellingBidRequest) {
        return Response.success(bidService.sellingBid(sellingBidRequest));
    }

    @GetMapping("/buy/{productNumber}")
    public Response<BuyingProductInfoResponse> getBuyingProductInfo(@PathVariable Long productNumber, @RequestParam String size) {
        return Response.success(bidService.getBuyingProductInfo(productNumber, size));
    }

    @PostMapping("/buy-bid")
    public Response<BuyingBidResponse> buyingBid(@RequestBody BuyingBidRequest buyingBidRequest) {
        return Response.success(bidService.buyingBid(buyingBidRequest));
    }

    @GetMapping("/bid-history")
    public Response<BidHistoryResponse> getBidHistory(@RequestParam String productNumber, @RequestParam String size) {
        return Response.success(bidService.getBidHistory(productNumber, size));
    }
}
