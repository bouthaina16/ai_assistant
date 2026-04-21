package com.example.ai_assistant.integration.stock.dto;

public record StockStationDto(
        String publicId,
        String name,
        String reference,
        String status,
        String type
) {
}

