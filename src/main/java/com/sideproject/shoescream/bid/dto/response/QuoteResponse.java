package com.sideproject.shoescream.bid.dto.response;

import lombok.Builder;

import java.time.LocalDate;
import java.util.LinkedHashMap;

@Builder
public record QuoteResponse(
        LinkedHashMap<LocalDate, Integer> quote
) {
}
