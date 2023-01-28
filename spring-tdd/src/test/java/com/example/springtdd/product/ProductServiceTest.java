package com.example.springtdd.product;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 검증 부분을 먼저 만드는 것이 쉽다!
 */
@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Test
    void 상품등록() {
        // given
        final AddProductRequest request = ProductSteps.상품등록요청_생성();

        // when
        // then
        assertDoesNotThrow(() -> productService.addProduct(request));
    }

    @Test
    void 상품조회() {
        // given
        productService.addProduct(ProductSteps.상품등록요청_생성());
        final Long productId = 1L;

        // when
        final GetProductResponse response = productService.getProduct(productId);

        // then
        assertThat(response).isNotNull();
    }

}
