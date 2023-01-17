package com.example.openfeign.resttemplate;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.openfeign.Currency;
import com.example.openfeign.ExchangeRateResponse;
import com.example.openfeign.config.ExchangeRateProperties;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ExchangeRateClient {
    private static final String API_KEY = "apikey";

    private final RestTemplate restTemplate;
    private final ExchangeRateProperties properties;

    public ExchangeRateResponse call(Currency source, Currency target) {
        return restTemplate.exchange(
                createApiUri(source, target),
                HttpMethod.GET,
                new HttpEntity<>(createHttpHeaders()),
                ExchangeRateResponse.class)
            .getBody();
    }

    private String createApiUri(Currency source, Currency target) {
        return UriComponentsBuilder.fromHttpUrl(properties.getUri())
            .queryParam("source", source.name())
            .queryParam("currencies", target.name())
            .encode()
            .toUriString();
    }

    private HttpHeaders createHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(API_KEY, properties.getKey());
        return headers;
    }
}
