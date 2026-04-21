package com.example.ai_assistant.integration.stock.dto;

import java.util.List;

public record StockMovementSearchResponseDto(
        List<StockMovementDto> productMovements,
        long count
) {
}

