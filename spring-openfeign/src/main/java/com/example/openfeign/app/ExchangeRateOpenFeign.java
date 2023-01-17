package com.example.openfeign.app;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.openfeign.Currency;
import com.example.openfeign.ExchangeRateResponse;

@FeignClient(name = "ExchangeRateOpenFeign", url = "${exchange.currency.api.uri}")
public interface ExchangeRateOpenFeign {

    @GetMapping
    ExchangeRateResponse call(
        @RequestHeader("apikey") String apiKey,
        @RequestParam("source") Currency source,
        @RequestParam("currencies") Currency currencies);

}
