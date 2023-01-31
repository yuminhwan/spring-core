package com.example.springtdd.product;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ProductTest {

    @Test
    void update() {
        // given
        final Product product = new Product("상품명", 1000, DiscountPolicy.NONE);

        // when
        product.update("상품 수정", 2000, DiscountPolicy.NONE);

        // then
        assertAll(
            () -> assertThat(product.getName()).isEqualTo("상품 수정"),
            () -> assertThat(product.getPrice()).isEqualTo(2000),
            () -> assertThat(product.getDiscountPolicy()).isEqualTo(DiscountPolicy.NONE)
        );
    }
}