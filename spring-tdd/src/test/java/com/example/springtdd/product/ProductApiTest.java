package com.example.springtdd.product;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.example.springtdd.ApiTest;

class ProductApiTest extends ApiTest {

    @Test
    void 상품등록() {
        // given
        final var request = ProductSteps.상품등록요청_생성();

        // when
        final var response = ProductSteps.상품등록요청(request);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    void 상품조회() {
        // given
        ProductSteps.상품등록요청(ProductSteps.상품등록요청_생성());
        Long productId = 1L;

        // when
        final var response = ProductSteps.상품조회요청(productId);

        // then
        assertAll(
            () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
            () -> assertThat(response.jsonPath().getString("name")).isEqualTo("상품명")
        );
    }
}
