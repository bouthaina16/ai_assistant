package com.example.ai_assistant.integration.stock.dto;

public record StockClientDto(
        String publicId,
        String name,
        String reference,
        String address,
        String email,
        String fiscalId,
        String tel
) {
}

