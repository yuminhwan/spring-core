package com.example.core.order;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.core.discount.FixDiscountPolicy;
import com.example.core.member.Grade;
import com.example.core.member.Member;
import com.example.core.member.MemoryMemberRepository;

class OrderServiceImplTest {
    
    @DisplayName("생성자 주입시 Mock 객체 - 순수한 자바 코드로도 가능")
    @Test
    void createOrder2() {
        MemoryMemberRepository memoryMemberRepository = new MemoryMemberRepository();
        memoryMemberRepository.save(new Member(1L, "name", Grade.VIP));

        OrderServiceImpl orderService = new OrderServiceImpl(memoryMemberRepository, new FixDiscountPolicy());
        Order order = orderService.createOrder(1L, "iteamA", 10000);
        assertThat(order.getDiscountPrice()).isEqualTo(1000);
    }

}
