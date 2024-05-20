package com.sideproject.shoescream.bid.controller;

import com.sideproject.shoescream.bid.dto.request.SellingBidRequest;
import com.sideproject.shoescream.bid.dto.response.SellingBidResponse;
import com.sideproject.shoescream.bid.dto.response.SellingProductInfoResponse;
import com.sideproject.shoescream.bid.service.SellingBidService;
import com.sideproject.shoescream.global.dto.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class SellingBidController {

    private final SellingBidService sellingBidService;

    @GetMapping("/sell/{productNumber}")
    public Response<SellingProductInfoResponse> getSellingProductInfo(@PathVariable Long productNumber, @RequestParam String size) {
        return Response.success(sellingBidService.getSellingProductInfo(productNumber, size));
    }

    @PostMapping("/sell-bid")
    public Response<SellingBidResponse> sellingBid(@RequestBody SellingBidRequest sellingBidRequest) {
        return Response.success(sellingBidService.sellingBid(sellingBidRequest));
    }
}
