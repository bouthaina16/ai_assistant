package com.example.ai_assistant.integration.stock.dto;

import java.util.List;

public record StockStationSearchResponseDto(
        List<StockStationDto> stations,
        long count
) {
}

