package com.example.core.discount;

import com.example.core.member.Member;

public class RateDiscountPolicy implements DiscountPolicy {

    private static final int discountPercent = 10;

    @Override
    public int discount(Member member, int price) {
        if (member.isVip()) {
            return price * discountPercent / 100;
        }
        return 0;
    }
}
