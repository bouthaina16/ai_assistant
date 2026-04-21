package com.example.ai_assistant.integration.stock.client.exception;

public class StockApiException extends RuntimeException {

    private final Integer statusCode;

    public StockApiException(String message, Integer statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public StockApiException(String message) {
        super(message);
        this.statusCode = null;
    }

    public StockApiException(String message, Throwable cause) {
        super(message, cause);
        this.statusCode = null;
    }

    public Integer getStatusCode() {
        return statusCode;
    }
}
