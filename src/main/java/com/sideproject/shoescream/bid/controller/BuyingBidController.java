package com.sideproject.shoescream.bid.controller;

import com.sideproject.shoescream.bid.dto.BuyingProductInfoResponse;
import com.sideproject.shoescream.bid.service.BuyingBidService;
import com.sideproject.shoescream.global.dto.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BuyingBidController {

    private final BuyingBidService buyingBidService;

    @GetMapping("/buy/{productNumber}")
    public Response<BuyingProductInfoResponse> getBuyingProductInfo(@PathVariable Long productNumber, @RequestParam String size) {
        return Response.success(buyingBidService.getBuyingProductInfo(productNumber, size));
    }
}
