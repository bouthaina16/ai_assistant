package com.example.ai_assistant.integration.stock.service;

import com.example.ai_assistant.integration.stock.client.StockDataClient;
import com.example.ai_assistant.integration.stock.dto.StockClientDto;
import com.example.ai_assistant.integration.stock.dto.StockInvoiceDto;
import com.example.ai_assistant.integration.stock.dto.StockMovementDto;
import com.example.ai_assistant.integration.stock.dto.StockProductDto;
import com.example.ai_assistant.integration.stock.dto.StockStationDto;
import com.example.ai_assistant.integration.stock.dto.StockTransformationDto;
import java.time.Instant;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class StockIntegrationService {

    private static final Logger log = LoggerFactory.getLogger(StockIntegrationService.class);

    private final StockDataClient stockDataClient;

    public StockIntegrationService(StockDataClient stockDataClient) {
        this.stockDataClient = stockDataClient;
    }

    public List<StockProductDto> getProducts() {
        return stockDataClient.getProducts();
    }

    public List<StockClientDto> getClients() {
        return stockDataClient.getClients();
    }

    public List<StockInvoiceDto> getInvoices() {
        return stockDataClient.getInvoices();
    }

    public List<StockStationDto> getStations() {
        return stockDataClient.getStations();
    }

    public List<StockTransformationDto> getTransformations() {
        return stockDataClient.getTransformations();
    }

    public List<StockMovementDto> getProductMovements() {
        return stockDataClient.getProductMovements();
    }

    public StockDataSnapshot fetchSnapshot() {
        long start = System.currentTimeMillis();

        List<StockProductDto> products = getProducts();
        List<StockClientDto> clients = getClients();
        List<StockInvoiceDto> invoices = getInvoices();
        List<StockStationDto> stations = getStations();
        List<StockTransformationDto> transformations = getTransformations();
        List<StockMovementDto> movements = getProductMovements();

        StockDataSnapshot snapshot = new StockDataSnapshot(
                Instant.now(),
                products,
                clients,
                invoices,
                stations,
                transformations,
                movements
        );

        log.info(
                "Stock snapshot fetched in {} ms [products={}, clients={}, invoices={}, stations={}, transformations={}, movements={}]",
                System.currentTimeMillis() - start,
                products.size(),
                clients.size(),
                invoices.size(),
                stations.size(),
                transformations.size(),
                movements.size()
        );

        return snapshot;
    }
}

