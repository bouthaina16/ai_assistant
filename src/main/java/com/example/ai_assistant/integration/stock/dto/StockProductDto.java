package com.example.ai_assistant.integration.stock.dto;

import java.math.BigDecimal;

public record StockProductDto(
        String publicId,
        String name,
        BigDecimal quantity,
        String unit,
        String type,
        String reference
) {
}

