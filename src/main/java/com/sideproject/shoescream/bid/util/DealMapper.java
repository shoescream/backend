package com.sideproject.shoescream.bid.util;

import com.sideproject.shoescream.bid.constant.DealStatus;
import com.sideproject.shoescream.bid.dto.response.DealHistoryResponse;
import com.sideproject.shoescream.bid.dto.response.DealResponse;
import com.sideproject.shoescream.bid.dto.response.QuoteResponse;
import com.sideproject.shoescream.bid.entity.Bid;
import com.sideproject.shoescream.bid.entity.Deal;
import com.sideproject.shoescream.product.entity.Product;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DealMapper {

    public static Deal toSuccessDeal(Bid bid, long buyerNumber) {
        LocalDateTime now = LocalDateTime.now();
        return Deal.builder()
                .buyerNumber(buyerNumber)
                .sellerNumber(bid.getMember().getMemberNumber())
                .product(bid.getProduct())
                .size(bid.getSize())
                .price(bid.getBidPrice())
                .dealStatus(DealStatus.SUCCESS_DEAL)
                .createdAt(now)
                .tradedAt(now)
                .isWriteReview(false)
                .build();
    }

    public static DealResponse toDealResponse(Deal deal) {
        return DealResponse.builder()
                .size(deal.getSize())
                .price(deal.getPrice())
                .tradedAt(deal.getTradedAt())
                .build();
    }

    public static Deal selllNowToDeal(Bid buyBidInfo, long sellerNumber) {
        return Deal.builder()
                .buyerNumber(buyBidInfo.getMember().getMemberNumber())
                .sellerNumber(sellerNumber)
                .product(buyBidInfo.getProduct())
                .size(buyBidInfo.getSize())
                .price(buyBidInfo.getBidPrice())
                .dealStatus(DealStatus.WAITING_DEPOSIT)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static QuoteResponse toQuoteResponse(List<Deal> deals, String size, int period) {
        Stream<Deal> dealStream = deals.stream();

        if (period > 0) {
            LocalDate startDate = LocalDate.now().minusMonths(period);
            dealStream = dealStream.filter(deal -> deal.getTradedAt().toLocalDate().isAfter(startDate));
        }

        LinkedHashMap<LocalDate, Integer> quote;
        if ("allSize".equals(size)) {
            return QuoteResponse.builder()
                    .quote(aggregateQuote(dealStream))
                    .build();
        }

        return QuoteResponse.builder()
                .quote(aggregateQuoteForSpecificSize(dealStream, size))
                .build();
    }

    public static DealHistoryResponse toDealHistoryResponse(Product product, String size) {
        if ("allSize".equals(size)) {
            return DealHistoryResponse.builder()
                    .dealResponse(aggregateDealResponse(product, size))
                    .build();
        }

        return DealHistoryResponse.builder()
                .dealResponse(aggregateDealResponseForSpecificSize(product, size))
                .build();
    }

    private static LinkedHashMap<LocalDate, Integer> aggregateQuote(Stream<Deal> dealStream) {
        return dealStream
                .collect(Collectors.groupingBy(
                        deal -> deal.getTradedAt().toLocalDate(),
                        LinkedHashMap::new,
                        Collectors.collectingAndThen(
                                Collectors.maxBy(Comparator.comparing(deal -> deal.getTradedAt().toLocalTime())),
                                latestDeal -> latestDeal.map(Deal::getPrice).orElse(0))));
    }

    private static LinkedHashMap<LocalDate, Integer> aggregateQuoteForSpecificSize(Stream<Deal> dealStream, String size) {
        return dealStream
                .filter(deal -> deal.getSize().equals(size))
                .collect(Collectors.groupingBy(
                        deal -> deal.getTradedAt().toLocalDate(),
                        LinkedHashMap::new,
                        Collectors.collectingAndThen(
                                Collectors.maxBy(Comparator.comparing(deal -> deal.getTradedAt().toLocalTime())),
                                latestDeal -> latestDeal.map(Deal::getPrice).orElse(0))));
    }

    public static List<DealResponse> aggregateDealResponse(Product product, String size) {
        return product.getDeals().stream()
                .filter(deal -> deal.getDealStatus().equals(DealStatus.SUCCESS_DEAL))
                .sorted(Comparator.comparing(Deal::getTradedAt).reversed())
                .map(DealMapper::toDealResponse)
                .limit(5)
                .toList();
    }

    public static List<DealResponse> aggregateDealResponseForSpecificSize(Product product, String size) {
        return product.getDeals().stream()
                .filter(deal -> deal.getDealStatus().equals(DealStatus.SUCCESS_DEAL))
                .filter(deal -> deal.getSize().equals(size))
                .sorted(Comparator.comparing(Deal::getTradedAt).reversed())
                .map(DealMapper::toDealResponse)
                .limit(5)
                .toList();
    }
}
