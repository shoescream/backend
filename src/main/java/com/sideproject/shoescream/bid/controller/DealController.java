package com.sideproject.shoescream.bid.controller;

import com.sideproject.shoescream.bid.dto.response.DealHistoryResponse;
import com.sideproject.shoescream.bid.dto.response.QuoteResponse;
import com.sideproject.shoescream.bid.service.DealService;
import com.sideproject.shoescream.global.dto.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DealController {

    private final DealService dealService;

    @GetMapping( "/quote")
    public Response<QuoteResponse> getQuote(@RequestParam String productNumber, @RequestParam String size, @RequestParam int period) {
        return Response.success(dealService.getQuote(productNumber, size, period));
    }

    @GetMapping("/deal-history")
    public Response<DealHistoryResponse> getDealHistory(@RequestParam String productNumber, @RequestParam String size) {
        return Response.success(dealService.getDealHistoryResponse(productNumber, size));
    }
}
