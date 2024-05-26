package com.sideproject.shoescream.bid.util;

import com.sideproject.shoescream.bid.dto.response.DealResponse;
import com.sideproject.shoescream.bid.dto.response.QuoteResponse;
import com.sideproject.shoescream.bid.entity.Deal;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DealMapper {

    public static DealResponse toDealResponse(Deal deal) {
        return DealResponse.builder()
                .size(deal.getSize())
                .price(deal.getPrice())
                .tradedAt(deal.getTradedAt())
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
}
