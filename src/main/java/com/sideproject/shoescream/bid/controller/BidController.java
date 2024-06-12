package com.sideproject.shoescream.bid.controller;

import com.sideproject.shoescream.bid.dto.request.BuyingBidRequest;
import com.sideproject.shoescream.bid.dto.request.SellingBidRequest;
import com.sideproject.shoescream.bid.dto.response.*;
import com.sideproject.shoescream.bid.service.BidService;
import com.sideproject.shoescream.global.dto.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BidController {

    private final BidService bidService;

    @GetMapping("/buy/{productNumber}")
    public Response<BuyingProductInfoResponse> getBuyingProductInfo(@PathVariable String productNumber, @RequestParam String size) {
        return Response.success(bidService.getBuyingProductInfo(productNumber, size));
    }

    @PostMapping("/buy")
    public Response<BuyingBidResponse> buyingBid(@RequestBody BuyingBidRequest buyingBidRequest, Authentication authentication) {
        return Response.success(bidService.buyingBid(buyingBidRequest, authentication.getName()));
    }

    @GetMapping("/sell/{productNumber}")
    public Response<SellingProductInfoResponse> getSellingProductInfo(@PathVariable String productNumber, @RequestParam String size) {
        return Response.success(bidService.getSellingProductInfo(productNumber, size));
    }

    @PostMapping("/sell")
    public Response<SellingBidResponse> sellingBid(@RequestBody SellingBidRequest sellingBidRequest, Authentication authentication) {
        return Response.success(bidService.sellingBid(sellingBidRequest, authentication.getName()));
    }

    @PostMapping("/sell-now")
    public Response<SellingBidResponse> sellingNow(@RequestBody SellingBidRequest sellingBidRequest, Authentication authentication) {
        return Response.success(bidService.sellingNow(sellingBidRequest, authentication.getName()));
    }

    @GetMapping("/bid-history")
    public Response<BidHistoryResponse> getBidHistory(@RequestParam String productNumber, @RequestParam String size) {
        return Response.success(bidService.getBidHistory(productNumber, size));
    }
}
