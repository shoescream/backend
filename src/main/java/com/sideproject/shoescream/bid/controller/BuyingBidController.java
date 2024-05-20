package com.sideproject.shoescream.bid.controller;

import com.sideproject.shoescream.bid.dto.request.BuyingBidRequest;
import com.sideproject.shoescream.bid.dto.response.BuyingBidResponse;
import com.sideproject.shoescream.bid.dto.response.BuyingProductInfoResponse;
import com.sideproject.shoescream.bid.service.BuyingBidService;
import com.sideproject.shoescream.global.dto.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BuyingBidController {

    private final BuyingBidService buyingBidService;

    @GetMapping("/buy/{productNumber}")
    public Response<BuyingProductInfoResponse> getBuyingProductInfo(@PathVariable Long productNumber, @RequestParam String size) {
        return Response.success(buyingBidService.getBuyingProductInfo(productNumber, size));
    }

    @PostMapping("/buy-bid")
    public Response<BuyingBidResponse> buyingBid(@RequestBody BuyingBidRequest buyingBidRequest) {
        return Response.success(buyingBidService.buyingBid(buyingBidRequest));
    }
}
