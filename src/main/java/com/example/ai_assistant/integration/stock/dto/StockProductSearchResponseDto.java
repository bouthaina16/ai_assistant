package com.example.ai_assistant.integration.stock.dto;

import java.util.List;

public record StockProductSearchResponseDto(
        List<StockProductDto> products,
        long count
) {
}

