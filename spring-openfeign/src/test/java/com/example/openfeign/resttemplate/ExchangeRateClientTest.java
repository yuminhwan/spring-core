package com.example.openfeign.resttemplate;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.openfeign.Currency;
import com.example.openfeign.ExchangeRateResponse;

@SpringBootTest
class ExchangeRateClientTest {

    @Autowired
    private ExchangeRateClient client;

    @Test
    void 환율_조회() {
        // given
        Currency source = Currency.USD;
        Currency target = Currency.KRW;

        // when
        ExchangeRateResponse result = client.call(source, target);

        // then
        assertThat(result.getSource()).isEqualTo(source);
    }

}