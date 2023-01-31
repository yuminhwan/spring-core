package com.example.springtdd.product;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * 검증 부분을 먼저 만드는 것이 쉽다!
 */
class ProductServiceTest {

    private final StubProductPort productPort = new StubProductPort();
    private ProductService productService;

    @BeforeEach
    void setUp() {
        productService = new ProductService(productPort);
    }

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
        final Long productId = 1L;
        final UpdateProductRequest request = new UpdateProductRequest("상품 수정", 2000, DiscountPolicy.NONE);
        final Product product = new Product("상품명", 1000, DiscountPolicy.NONE);
        productPort.getProduct_will_return = product;

        // when
        productService.updateProduct(productId, request);

        // then
        assertThat(product.getName()).isEqualTo("상품 수정");
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
