package com.example.springtdd.product;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

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
        final GetProductResponse response = productService.getProduct(productId).getBody();

        // then
        assertThat(response).isNotNull();
    }

    @Test
    void 상품수정() {
        // given
        productService.addProduct(ProductSteps.상품등록요청_생성());
        final Long productId = 1L;
        final UpdateProductRequest request = ProductSteps.상품수정요청_생성();

        // when
        productService.updateProduct(productId, request);

        // then
        final ResponseEntity<GetProductResponse> response = productService.getProduct(productId);
        final GetProductResponse productResponse = response.getBody();
        assertAll(
            () -> assertThat(productResponse.name()).isEqualTo("상품 수정"),
            () -> assertThat(productResponse.price()).isEqualTo(2000)
        );
    }

    private static class StubProductPort implements ProductPort {
        public Product getProduct_will_return;

        @Override
        public void save(final Product product) {

        }

        @Override
        public Product getProduct(final Long productId) {
            return getProduct_will_return;
        }
    }
}
