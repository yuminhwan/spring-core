package com.example.core.scan;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.example.core.AutoAppConfig;
import com.example.core.member.MemberRepository;
import com.example.core.member.MemberService;
import com.example.core.order.OrderServiceImpl;

class AutoAppConfigTest {

    @DisplayName("컴포넌트 스캔 작동 테스트")
    @Test
    void basicScan() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);

        MemberService memberService = ac.getBean(MemberService.class);
        assertThat(memberService).isInstanceOf(MemberService.class);

        OrderServiceImpl bean = ac.getBean(OrderServiceImpl.class);
        MemberRepository memberRepository = bean.getMemberRepository();
        assertThat(memberRepository).isNotNull();
    }

}
