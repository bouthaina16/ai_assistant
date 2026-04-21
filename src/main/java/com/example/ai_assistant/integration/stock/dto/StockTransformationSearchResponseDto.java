package com.example.ai_assistant.integration.stock.dto;

import java.util.List;

public record StockTransformationSearchResponseDto(
        List<StockTransformationDto> transformations,
        long count
) {
}

