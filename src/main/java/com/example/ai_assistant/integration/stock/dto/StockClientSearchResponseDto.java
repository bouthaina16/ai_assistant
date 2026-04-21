package com.example.ai_assistant.integration.stock.dto;

import java.util.List;

public record StockClientSearchResponseDto(
        List<StockClientDto> clients,
        long count
) {
}

