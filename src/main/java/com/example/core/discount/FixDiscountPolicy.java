package com.example.core.discount;

import com.example.core.member.Member;

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
