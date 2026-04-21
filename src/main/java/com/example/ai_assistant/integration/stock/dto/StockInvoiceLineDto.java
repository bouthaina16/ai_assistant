package com.example.ai_assistant.integration.stock.dto;

import java.math.BigDecimal;

public record StockInvoiceLineDto(
        String productPublicId,
        BigDecimal quantity,
        BigDecimal price,
        BigDecimal discount,
        BigDecimal tax
) {
}

