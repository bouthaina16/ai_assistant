package com.example.ai_assistant.integration.stock.client;

import com.example.ai_assistant.integration.stock.client.exception.StockApiException;
import com.example.ai_assistant.integration.stock.config.StockApiProperties;
import com.example.ai_assistant.integration.stock.dto.StockClientDto;
import com.example.ai_assistant.integration.stock.dto.StockClientSearchResponseDto;
import com.example.ai_assistant.integration.stock.dto.StockInvoiceDto;
import com.example.ai_assistant.integration.stock.dto.StockInvoiceSearchResponseDto;
import com.example.ai_assistant.integration.stock.dto.StockMovementDto;
import com.example.ai_assistant.integration.stock.dto.StockMovementSearchResponseDto;
import com.example.ai_assistant.integration.stock.dto.StockProductDto;
import com.example.ai_assistant.integration.stock.dto.StockProductSearchResponseDto;
import com.example.ai_assistant.integration.stock.dto.StockSearchRequestDto;
import com.example.ai_assistant.integration.stock.dto.StockStationDto;
import com.example.ai_assistant.integration.stock.dto.StockStationSearchResponseDto;
import com.example.ai_assistant.integration.stock.dto.StockTransformationDto;
import com.example.ai_assistant.integration.stock.dto.StockTransformationSearchResponseDto;
import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class StockDataClient {

    private static final Logger log = LoggerFactory.getLogger(StockDataClient.class);

    private final WebClient webClient;
    private final StockAuthClient stockAuthClient;
    private final StockApiProperties properties;

    public StockDataClient(
            @Qualifier("stockApiWebClient") WebClient stockApiWebClient,
            StockAuthClient stockAuthClient,
            StockApiProperties properties
    ) {
        this.webClient = stockApiWebClient;
        this.stockAuthClient = stockAuthClient;
        this.properties = properties;
    }

    public List<StockProductDto> getProducts() {
        List<StockProductDto> rawProducts = getProductsByType("RAW");
        List<StockProductDto> finalProducts = getProductsByType("FINAL");

        if (rawProducts.isEmpty() && finalProducts.isEmpty()) {
            StockProductSearchResponseDto fallback = postWithAuthRetry(
                    properties.getEndpoints().getProductSearch(),
                    StockSearchRequestDto.defaultRequest(properties.getDefaultPageSize()),
                    StockProductSearchResponseDto.class,
                    "products(fallback)"
            );
            return fallback == null || fallback.products() == null ? List.of() : fallback.products();
        }

        Map<String, StockProductDto> merged = new LinkedHashMap<>();
        rawProducts.forEach(product -> merged.put(product.publicId(), product));
        finalProducts.forEach(product -> merged.put(product.publicId(), product));
        return List.copyOf(merged.values());
    }

    private List<StockProductDto> getProductsByType(String type) {
        Map<String, Object> request = new LinkedHashMap<>();
        request.put("types", List.of(type));
        request.put("page", 0);
        request.put("pageSize", properties.getDefaultPageSize());
        StockProductSearchResponseDto response = postWithAuthRetry(
                properties.getEndpoints().getProductSearch(),
                request,
                StockProductSearchResponseDto.class,
                "products(" + type + ")"
        );
        return response == null || response.products() == null ? List.of() : response.products();
    }

    public List<StockClientDto> getClients() {
        StockSearchRequestDto request = StockSearchRequestDto.defaultRequest(properties.getDefaultPageSize());
        StockClientSearchResponseDto response = postWithAuthRetry(
                properties.getEndpoints().getClientSearch(),
                request,
                StockClientSearchResponseDto.class,
                "clients"
        );
        return response == null || response.clients() == null ? List.of() : response.clients();
    }

    public List<StockInvoiceDto> getInvoices() {
        StockSearchRequestDto request = StockSearchRequestDto.defaultRequest(properties.getDefaultPageSize());
        StockInvoiceSearchResponseDto response = postWithAuthRetry(
                properties.getEndpoints().getInvoiceSearch(),
                request,
                StockInvoiceSearchResponseDto.class,
                "invoices"
        );
        return response == null || response.invoices() == null ? List.of() : response.invoices();
    }

    public List<StockStationDto> getStations() {
        StockSearchRequestDto request = StockSearchRequestDto.defaultRequest(properties.getDefaultPageSize());
        StockStationSearchResponseDto response = postWithAuthRetry(
                properties.getEndpoints().getStationSearch(),
                request,
                StockStationSearchResponseDto.class,
                "stations"
        );
        return response == null || response.stations() == null ? List.of() : response.stations();
    }

    public List<StockTransformationDto> getTransformations() {
        StockSearchRequestDto request = StockSearchRequestDto.defaultRequest(properties.getDefaultPageSize());
        StockTransformationSearchResponseDto response = postWithAuthRetry(
                properties.getEndpoints().getTransformationSearch(),
                request,
                StockTransformationSearchResponseDto.class,
                "transformations"
        );
        return response == null || response.transformations() == null ? List.of() : response.transformations();
    }

    public List<StockMovementDto> getProductMovements() {
        StockSearchRequestDto request = StockSearchRequestDto.defaultRequest(properties.getDefaultPageSize());
        StockMovementSearchResponseDto response = postWithAuthRetry(
                properties.getEndpoints().getMovementSearch(),
                request,
                StockMovementSearchResponseDto.class,
                "productMovements"
        );
        return response == null || response.productMovements() == null ? List.of() : response.productMovements();
    }

    private <T> T postWithAuthRetry(String path, Object body, Class<T> responseType, String operationName) {
        String token = stockAuthClient.getValidToken();
        try {
            return executePost(path, body, responseType, token, operationName);
        } catch (StockApiException ex) {
            if (ex.getStatusCode() != null && (ex.getStatusCode() == 401 || ex.getStatusCode() == 403)) {
                log.warn("Stock API call '{}' unauthorized; refreshing token and retrying", operationName);
                stockAuthClient.invalidateToken();
                String refreshed = stockAuthClient.getValidToken();
                return executePost(path, body, responseType, refreshed, operationName + "(retry)");
            }
            throw ex;
        }
    }

    private <T> T executePost(String path, Object body, Class<T> responseType, String token, String operationName) {
        long start = System.currentTimeMillis();
        try {
            T response = webClient.post()
                    .uri(path)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .bodyValue(body)
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, clientResponse -> clientResponse
                            .bodyToMono(String.class)
                            .defaultIfEmpty("")
                            .map(errorBody -> new StockApiException(
                                    "Stock API error during " + operationName + " status=" + clientResponse.statusCode().value()
                                            + " body=" + sanitize(errorBody),
                                    clientResponse.statusCode().value()
                            )))
                    .bodyToMono(responseType)
                    .timeout(Duration.ofMillis(properties.getRequestTimeoutMillis()))
                    .block();

            log.debug("Stock API call '{}' completed in {} ms", operationName, System.currentTimeMillis() - start);
            return response;
        } catch (StockApiException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new StockApiException("Stock API call failed during " + operationName, ex);
        }
    }

    private String sanitize(String input) {
        if (input == null) {
            return "";
        }
        return input.length() > 300 ? input.substring(0, 300) + "..." : input;
    }
}

