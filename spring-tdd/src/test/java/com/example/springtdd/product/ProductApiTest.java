package com.example.springtdd.product;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.example.springtdd.ApiTest;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

class ProductApiTest extends ApiTest {

    @Test
    void 상품등록() {
        // given
        final AddProductRequest request = 상품등록요청_생성();

        // when
        final ExtractableResponse<Response> response = RestAssured.given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .post("/products")
            .then()
            .log().all().extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    private AddProductRequest 상품등록요청_생성() {
        final String name = "상품명";
        final int price = 1000;
        final DiscountPolicy discountPolicy = DiscountPolicy.NONE;
        return new AddProductRequest(name, price, discountPolicy);
    }
}
