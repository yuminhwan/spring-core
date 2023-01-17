package com.example.openfeign.app;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.openfeign.Currency;
import com.example.openfeign.ExchangeRateResponse;
import com.example.openfeign.config.ExchangeRateProperties;
import com.example.openfeign.utils.FeignTest;

@FeignTest
class ExchangeRateOpenFeignTest {

    @Autowired
    private ExchangeRateOpenFeign client;

    @Autowired
    private ExchangeRateProperties properties;

    @Test
    void 환율_조회() {
        // given
        Currency source = Currency.USD;
        Currency target = Currency.KRW;

        // when
        ExchangeRateResponse result = client.call(properties.getKey(), source, target);

        // then
        assertThat(result.getSource()).isEqualTo(source);
    }

}