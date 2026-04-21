package com.example.ai_assistant.integration.stock.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

public record StockInvoiceDto(
        String publicId,
        String reference,
        String clientPublicId,
        OffsetDateTime date,
        BigDecimal total,
        List<StockInvoiceLineDto> productInvoices
) {
}

