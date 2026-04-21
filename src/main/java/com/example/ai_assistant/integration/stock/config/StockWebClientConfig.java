package com.example.ai_assistant.integration.stock.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableConfigurationProperties(StockApiProperties.class)
public class StockWebClientConfig {

    @Bean("stockApiWebClient")
    public WebClient stockApiWebClient(StockApiProperties properties) {
        ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(4 * 1024 * 1024))
                .build();

        return WebClient.builder()
                .baseUrl(properties.getBaseUrl())
                .exchangeStrategies(exchangeStrategies)
                .build();
    }
}

