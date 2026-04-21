package com.example.ai_assistant.integration.stock.client.exception;

public class StockAuthenticationException extends RuntimeException {

    public StockAuthenticationException(String message) {
        super(message);
    }

    public StockAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}

