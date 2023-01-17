package com.example.openfeign.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@ConstructorBinding
@ConfigurationProperties("exchange.currency.api")
@RequiredArgsConstructor
public class ExchangeRateProperties {

    private final String uri;
    private final String key;
}
