package com.example.ai_assistant.integration.stock.service;

import com.example.ai_assistant.integration.stock.dto.StockClientDto;
import com.example.ai_assistant.integration.stock.dto.StockInvoiceDto;
import com.example.ai_assistant.integration.stock.dto.StockMovementDto;
import com.example.ai_assistant.integration.stock.dto.StockProductDto;
import com.example.ai_assistant.integration.stock.dto.StockStationDto;
import com.example.ai_assistant.integration.stock.dto.StockTransformationDto;
import java.time.Instant;
import java.util.List;

public record StockDataSnapshot(
        Instant fetchedAt,
        List<StockProductDto> products,
        List<StockClientDto> clients,
        List<StockInvoiceDto> invoices,
        List<StockStationDto> stations,
        List<StockTransformationDto> transformations,
        List<StockMovementDto> productMovements
) {
}

