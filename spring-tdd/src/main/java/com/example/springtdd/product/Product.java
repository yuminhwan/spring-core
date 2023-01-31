package com.example.springtdd.product;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.util.Assert;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "products")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int price;
    private DiscountPolicy discountPolicy;

    public Product(final String name, final int price, final DiscountPolicy discountPolicy) {
        Assert.hasText(name, "상품명은 필수입니다.");
        Assert.isTrue(price > 0, "상품 가격은 0보다 커야 합니다.");
        Assert.notNull(discountPolicy, "할인 정책은 필수 입니다.");
        this.name = name;
        this.price = price;
        this.discountPolicy = discountPolicy;
    }

    // 도메인 로직! -> 테스트 필수
    public void update(final String name, final int price, final DiscountPolicy discountPolicy) {
        Assert.hasText(name, "상품명은 필수입니다.");
        Assert.isTrue(price > 0, "상품 가격은 0보다 커야 합니다.");
        Assert.notNull(discountPolicy, "할인 정책은 필수 입니다.");
        this.name = name;
        this.price = price;
        this.discountPolicy = discountPolicy;
    }
}
