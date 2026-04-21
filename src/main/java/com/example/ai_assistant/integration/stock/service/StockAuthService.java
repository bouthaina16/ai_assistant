package com.example.ai_assistant.integration.stock.service;

import com.example.ai_assistant.integration.stock.client.StockAuthClient;
import org.springframework.stereotype.Service;

@Service
public class StockAuthService {

    private final StockAuthClient stockAuthClient;

    public StockAuthService(StockAuthClient stockAuthClient) {
        this.stockAuthClient = stockAuthClient;
    }

    public String getValidToken() {
        return stockAuthClient.getValidToken();
    }

    public void invalidateToken() {
        stockAuthClient.invalidateToken();
    }
}

