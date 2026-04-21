package com.example.ai_assistant.integration.stock.dto;

import java.util.Map;

public record StockSearchRequestDto(
        int page,
        int pageSize,
        Map<String, Object> filters
) {

    public static StockSearchRequestDto defaultRequest(int pageSize) {
        return new StockSearchRequestDto(0, pageSize, Map.of());
    }

    public static StockSearchRequestDto withFilters(int pageSize, Map<String, Object> filters) {
        return new StockSearchRequestDto(0, pageSize, filters == null ? Map.of() : filters);
    }
}

