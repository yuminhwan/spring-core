package com.example.springtdd;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

class ProductServiceTest {

    private ProductService productService;
    private ProductPort productPort;
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = new ProductRepository();
        productPort = new ProductAdapter(productRepository);
        productService = new ProductService(productPort);
    }

    @Test
    void 상품등록() {
        // given
        final String name = "상품명";
        final int price = 1000;
        final DiscountPolicy discountPolicy = DiscountPolicy.NONE;
        final AddProductRequest request = new AddProductRequest(name, price, discountPolicy);

        // when
        productService.addProduct(request);

        // then
    }

    private enum DiscountPolicy {
        NONE
    }

    private interface ProductPort {
        void save(Product product);
    }

    private record AddProductRequest(String name, int price, DiscountPolicy discountPolicy) {
        private AddProductRequest {
            Assert.hasText(name, "상품명은 필수입니다.");
            Assert.isTrue(price > 0, "상품 가격은 0보다 커야 합니다.");
            Assert.notNull(discountPolicy, "할인 정책은 필수 입니다.");
        }
    }

    private class ProductService {
        private final ProductPort productPort;

        private ProductService(final ProductPort productPort) {
            this.productPort = productPort;
        }

        public void addProduct(final AddProductRequest request) {
            final Product product = new Product(request.name(), request.price(), request.discountPolicy());

            productPort.save(product);
        }
    }

    private class Product {
        private final String name;
        private final int price;
        private final DiscountPolicy discountPolicy;
        private Long id;

        public Product(final String name, final int price, final DiscountPolicy discountPolicy) {
            Assert.hasText(name, "상품명은 필수입니다.");
            Assert.isTrue(price > 0, "상품 가격은 0보다 커야 합니다.");
            Assert.notNull(discountPolicy, "할인 정책은 필수 입니다.");
            this.name = name;
            this.price = price;
            this.discountPolicy = discountPolicy;
        }

        public void assignId(final Long id) {
            this.id = id;
        }

        public Long getId() {
            return id;
        }
    }

    private class ProductAdapter implements ProductPort {
        private final ProductRepository productRepository;

        private ProductAdapter(final ProductRepository productRepository) {
            this.productRepository = productRepository;
        }

        @Override
        public void save(final Product product) {
            productRepository.save(product);
        }
    }

    private class ProductRepository {
        private final Map<Long, Product> persistence = new ConcurrentHashMap<>();
        private Long sequence = 0L;

        public void save(final Product product) {
            product.assignId(++sequence);
            persistence.put(product.getId(), product);
        }
    }
}
