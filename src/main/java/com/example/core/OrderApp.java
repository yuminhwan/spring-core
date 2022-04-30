package com.example.core;

import com.example.core.Order.Order;
import com.example.core.Order.OrderService;
import com.example.core.member.Grade;
import com.example.core.member.Member;
import com.example.core.member.MemberService;

public class OrderApp {
    public static void main(String[] args) {
        AppConfig appConfig = new AppConfig();
        MemberService memberService = appConfig.memberService();
        OrderService orderService = appConfig.orderService();

        long memberId = 1L;
        Member member = new Member(memberId, "memberA", Grade.VIP);
        memberService.join(member);

        Order order = orderService.createOrder(memberId, "itemA", 10000);
        
        System.out.println("order = " + order);
    }
}
