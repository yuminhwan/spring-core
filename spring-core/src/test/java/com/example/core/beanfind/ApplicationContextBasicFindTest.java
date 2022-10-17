package com.example.core.beanfind;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.example.core.AppConfig;
import com.example.core.member.MemberService;
import com.example.core.member.MemberServiceImpl;

public class ApplicationContextBasicFindTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    @DisplayName("빈 이름으로 조회")
    @Test
    void findBeanByName() {
        MemberService memberService = ac.getBean("memberService", MemberService.class);
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    @DisplayName("이름 없이 타입만으로 조회")
    @Test
    void findBeanByType() {
        MemberService memberService = ac.getBean(MemberService.class);
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    @DisplayName("구체 타입으로 조회")
    @Test
    void findBeanByName2() {
        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    @DisplayName("해당 이름으로 등록된 빈이 없다면 NoSuchBeanDefinitionException 예외를 발생한다.")
    @Test
    void findBeanByNameX() {
        assertThatThrownBy(() -> ac.getBean("xxxx", MemberService.class))
            .isInstanceOf(NoSuchBeanDefinitionException.class);
    }

}
