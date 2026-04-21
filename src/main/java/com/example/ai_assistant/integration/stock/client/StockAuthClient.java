package com.example.ai_assistant.integration.stock.client;

import com.example.ai_assistant.integration.stock.client.exception.StockApiException;
import com.example.ai_assistant.integration.stock.client.exception.StockAuthenticationException;
import com.example.ai_assistant.integration.stock.config.StockApiProperties;
import com.example.ai_assistant.integration.stock.dto.StockAuthRequestDto;
import com.example.ai_assistant.integration.stock.dto.StockAuthResponseDto;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class StockAuthClient {

    private static final Logger log = LoggerFactory.getLogger(StockAuthClient.class);
    private static final Pattern JWT_EXP_PATTERN = Pattern.compile("\\\"exp\\\"\\s*:\\s*(\\d+)");

    private final WebClient webClient;
    private final StockApiProperties properties;
    private final ReentrantLock refreshLock = new ReentrantLock();

    private volatile TokenState tokenState;

    public StockAuthClient(
            @Qualifier("stockApiWebClient") WebClient stockApiWebClient,
            StockApiProperties properties
    ) {
        this.webClient = stockApiWebClient;
        this.properties = properties;
    }

    public String getValidToken() {
        TokenState current = tokenState;
        if (current != null && !isExpiringSoon(current.expireAt())) {
            return current.token();
        }

        refreshLock.lock();
        try {
            TokenState secondCheck = tokenState;
            if (secondCheck != null && !isExpiringSoon(secondCheck.expireAt())) {
                return secondCheck.token();
            }

            TokenState refreshed = authenticate();
            tokenState = refreshed;
            return refreshed.token();
        } finally {
            refreshLock.unlock();
        }
    }

    public void invalidateToken() {
        tokenState = null;
    }

    private TokenState authenticate() {
        StockAuthRequestDto request = new StockAuthRequestDto(properties.getLogin(), properties.getPassword());
        String endpoint = properties.getEndpoints().getAuthLogin();
        long startedAt = System.currentTimeMillis();

        try {
            StockAuthResponseDto response = webClient
                    .post()
                    .uri(endpoint)
                    .bodyValue(request)
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, clientResponse -> clientResponse
                            .bodyToMono(String.class)
                            .defaultIfEmpty("")
                            .map(body -> new StockAuthenticationException(
                                    "Stock authentication failed with status " + clientResponse.statusCode().value()
                                            + ", body=" + sanitize(body)
                            )))
                    .bodyToMono(StockAuthResponseDto.class)
                    .block();

            if (response == null || response.accessToken() == null || response.accessToken().isBlank()) {
                throw new StockAuthenticationException("Stock authentication returned an empty token");
            }

            Instant expireAt = resolveTokenExpiration(response.accessToken());
            log.info("Stock auth succeeded in {} ms; token expires at {}", System.currentTimeMillis() - startedAt, expireAt);
            return new TokenState(response.accessToken(), expireAt);
        } catch (StockAuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new StockApiException("Stock authentication call failed", ex);
        }
    }

    private boolean isExpiringSoon(Instant expireAt) {
        Instant boundary = Instant.now().plusSeconds(properties.getTokenRefreshSkewSeconds());
        return expireAt.isBefore(boundary);
    }

    private Instant resolveTokenExpiration(String jwtToken) {
        try {
            String[] chunks = jwtToken.split("\\.");
            if (chunks.length < 2) {
                return Instant.now().plusSeconds(properties.getAuthTtlFallbackSeconds());
            }
            byte[] decoded = Base64.getUrlDecoder().decode(chunks[1]);
            String payload = new String(decoded, StandardCharsets.UTF_8);

            Matcher matcher = JWT_EXP_PATTERN.matcher(payload);
            if (matcher.find()) {
                long exp = Long.parseLong(matcher.group(1));
                return Instant.ofEpochSecond(exp);
            }
            return Instant.now().plusSeconds(properties.getAuthTtlFallbackSeconds());
        } catch (Exception ex) {
            log.debug("Failed to parse JWT expiration, using fallback TTL", ex);
            return Instant.now().plusSeconds(properties.getAuthTtlFallbackSeconds());
        }
    }

    private String sanitize(String input) {
        if (input == null) {
            return "";
        }
        return input.length() > 300 ? input.substring(0, 300) + "..." : input;
    }

    private record TokenState(String token, Instant expireAt) {
    }
}

