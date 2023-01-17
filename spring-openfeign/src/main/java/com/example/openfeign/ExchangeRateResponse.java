package com.example.openfeign;

import java.util.Map;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class ExchangeRateResponse {

    private final long timestamp;
    private final Currency source;
    private final Map<String, Double> quotes;
}
