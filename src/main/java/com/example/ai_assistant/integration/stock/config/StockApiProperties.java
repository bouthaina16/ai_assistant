package com.example.ai_assistant.integration.stock.config;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "integration.stock")
public class StockApiProperties {

    @NotBlank
    private String baseUrl;

    @NotBlank
    private String login;

    @NotBlank
    private String password;

    @Min(100)
    private int connectTimeoutMillis = 5_000;

    @Min(100)
    private int responseTimeoutMillis = 10_000;

    @Min(100)
    private int requestTimeoutMillis = 15_000;

    @Min(0)
    private int tokenRefreshSkewSeconds = 60;

    @Min(60)
    private int authTtlFallbackSeconds = 3_000;

    @Min(1)
    private int defaultPageSize = 500;

    private Endpoints endpoints = new Endpoints();

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getConnectTimeoutMillis() {
        return connectTimeoutMillis;
    }

    public void setConnectTimeoutMillis(int connectTimeoutMillis) {
        this.connectTimeoutMillis = connectTimeoutMillis;
    }

    public int getResponseTimeoutMillis() {
        return responseTimeoutMillis;
    }

    public void setResponseTimeoutMillis(int responseTimeoutMillis) {
        this.responseTimeoutMillis = responseTimeoutMillis;
    }

    public int getRequestTimeoutMillis() {
        return requestTimeoutMillis;
    }

    public void setRequestTimeoutMillis(int requestTimeoutMillis) {
        this.requestTimeoutMillis = requestTimeoutMillis;
    }

    public int getTokenRefreshSkewSeconds() {
        return tokenRefreshSkewSeconds;
    }

    public void setTokenRefreshSkewSeconds(int tokenRefreshSkewSeconds) {
        this.tokenRefreshSkewSeconds = tokenRefreshSkewSeconds;
    }

    public int getAuthTtlFallbackSeconds() {
        return authTtlFallbackSeconds;
    }

    public void setAuthTtlFallbackSeconds(int authTtlFallbackSeconds) {
        this.authTtlFallbackSeconds = authTtlFallbackSeconds;
    }

    public int getDefaultPageSize() {
        return defaultPageSize;
    }

    public void setDefaultPageSize(int defaultPageSize) {
        this.defaultPageSize = defaultPageSize;
    }

    public Endpoints getEndpoints() {
        return endpoints;
    }

    public void setEndpoints(Endpoints endpoints) {
        this.endpoints = endpoints;
    }

    public static class Endpoints {

        private String authLogin = "/auth/login";
        private String productSearch = "/product/search";
        private String clientSearch = "/client/search";
        private String invoiceSearch = "/invoice/search";
        private String stationSearch = "/station/search";
        private String transformationSearch = "/transformation/search";
        private String movementSearch = "/product-movement/search";

        public String getAuthLogin() {
            return authLogin;
        }

        public void setAuthLogin(String authLogin) {
            this.authLogin = authLogin;
        }

        public String getProductSearch() {
            return productSearch;
        }

        public void setProductSearch(String productSearch) {
            this.productSearch = productSearch;
        }

        public String getClientSearch() {
            return clientSearch;
        }

        public void setClientSearch(String clientSearch) {
            this.clientSearch = clientSearch;
        }

        public String getInvoiceSearch() {
            return invoiceSearch;
        }

        public void setInvoiceSearch(String invoiceSearch) {
            this.invoiceSearch = invoiceSearch;
        }

        public String getStationSearch() {
            return stationSearch;
        }

        public void setStationSearch(String stationSearch) {
            this.stationSearch = stationSearch;
        }

        public String getTransformationSearch() {
            return transformationSearch;
        }

        public void setTransformationSearch(String transformationSearch) {
            this.transformationSearch = transformationSearch;
        }

        public String getMovementSearch() {
            return movementSearch;
        }

        public void setMovementSearch(String movementSearch) {
            this.movementSearch = movementSearch;
        }
    }
}

