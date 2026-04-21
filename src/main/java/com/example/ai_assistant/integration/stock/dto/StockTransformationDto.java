package com.example.ai_assistant.integration.stock.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record StockTransformationDto(
        String publicId,
        String type,
        String productPublicId,
        String workStationPublicId,
        BigDecimal quantity,
        OffsetDateTime createdAt
) {
}

