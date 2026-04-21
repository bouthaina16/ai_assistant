package com.example.ai_assistant.integration.stock.controller;

import com.example.ai_assistant.integration.stock.dto.StockClientDto;
import com.example.ai_assistant.integration.stock.dto.StockClientSearchResponseDto;
import com.example.ai_assistant.integration.stock.dto.StockInvoiceDto;
import com.example.ai_assistant.integration.stock.dto.StockInvoiceSearchResponseDto;
import com.example.ai_assistant.integration.stock.dto.StockProductDto;
import com.example.ai_assistant.integration.stock.dto.StockProductSearchResponseDto;
import com.example.ai_assistant.integration.stock.service.StockDataSnapshot;
import com.example.ai_assistant.integration.stock.service.StockIntegrationService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai/stock")
public class StockIntegrationController {

    private final StockIntegrationService stockIntegrationService;

    public StockIntegrationController(StockIntegrationService stockIntegrationService) {
        this.stockIntegrationService = stockIntegrationService;
    }

    @GetMapping("/products")
    public StockProductSearchResponseDto products() {
        List<StockProductDto> products = stockIntegrationService.getProducts();
        return new StockProductSearchResponseDto(products, products.size());
    }

    @GetMapping("/clients")
    public StockClientSearchResponseDto clients() {
        List<StockClientDto> clients = stockIntegrationService.getClients();
        return new StockClientSearchResponseDto(clients, clients.size());
    }

    @GetMapping("/invoices")
    public StockInvoiceSearchResponseDto invoices() {
        List<StockInvoiceDto> invoices = stockIntegrationService.getInvoices();
        return new StockInvoiceSearchResponseDto(invoices, invoices.size());
    }

    @GetMapping("/snapshot")
    public StockDataSnapshot snapshot() {
        return stockIntegrationService.fetchSnapshot();
    }
}

