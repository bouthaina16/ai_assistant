package com.example.ai_assistant.integration.stock.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record StockMovementDto(
        String publicId,
        String productPublicId,
        String productName,
        String movementType,
        BigDecimal quantity,
        BigDecimal oldStock,
        BigDecimal newStock,
        String sourceType,
        String sourceReference,
        OffsetDateTime movementDate,
        String comment,
        OffsetDateTime createdAt
) {
}

