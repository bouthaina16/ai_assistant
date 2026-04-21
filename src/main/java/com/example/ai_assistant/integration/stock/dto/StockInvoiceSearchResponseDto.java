package com.example.ai_assistant.integration.stock.dto;

import java.util.List;

public record StockInvoiceSearchResponseDto(
        List<StockInvoiceDto> invoices,
        long count
) {
}

