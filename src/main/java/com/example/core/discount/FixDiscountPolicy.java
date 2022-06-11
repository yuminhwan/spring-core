package com.example.core.discount;

import org.springframework.stereotype.Component;

import com.example.core.member.Member;

@Component
// @Qualifier("fixDiscountPolicy")
public class FixDiscountPolicy implements DiscountPolicy {

    private static final int discountFixAmount = 1000;

    @Override
    public int discount(Member member, int price) {
        if (member.isVip()) {
            return discountFixAmount;
        }

        return 0;
    }
}
