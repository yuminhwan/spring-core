package com.example.springdb.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import javax.sql.DataSource;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import com.example.springdb.domain.Member;
import com.example.springdb.repository.MemberRepository;
import com.example.springdb.repository.MemberRepositoryV5;

import lombok.extern.slf4j.Slf4j;

/**
 * 예외 누수 문제 해결
 * SQLException 제거
 * MemberRepository 인터페이스 의존
 * JdbcTemplate 사용
 */
@Slf4j
@SpringBootTest
class MemberServiceV4Test {

    private static final String MEMBER_A = "memberA";
    private static final String MEMBER_B = "memberB";
    private static final String MEMBER_EX = "ex";

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberServiceV4 memberService;

    @AfterEach
    void tearDown() {
        memberRepository.delete(MEMBER_A);
        memberRepository.delete(MEMBER_B);
        memberRepository.delete(MEMBER_EX);
    }

    @DisplayName("Proxy 객체 확인")
    @Test
    void AopCheck() {
        log.info("memberService class = {}", memberService.getClass());
        log.info("memberRepository class = {}", memberRepository.getClass());

        assertAll(
            () -> assertThat(AopUtils.isAopProxy(memberService)).isTrue(),
            () -> assertThat(AopUtils.isAopProxy(memberRepository)).isFalse()
        );
    }

    @DisplayName("계좌 이체 성공")
    @Test
    void accountTransfer() {
        // given
        Member memberA = new Member(MEMBER_A, 10_000);
        Member memberB = new Member(MEMBER_B, 10_000);
        memberRepository.save(memberA);
        memberRepository.save(memberB);

        // when
        log.info("START TX");
        memberService.accountTransfer(memberA.getMemberId(), memberB.getMemberId(), 2000);
        log.info("END TX");

        // then
        Member findMemberA = memberRepository.findById(memberA.getMemberId());
        Member findMemberB = memberRepository.findById(memberB.getMemberId());
        assertAll(
            () -> assertThat(findMemberA.getMoney()).isEqualTo(8_000),
            () -> assertThat(findMemberB.getMoney()).isEqualTo(12_000)
        );
    }

    @DisplayName("이체중 예외 발생 - 롤백 성공")
    @Test
    void accountTransferEx() {
        Member memberA = new Member(MEMBER_A, 10_000);
        Member memberEX = new Member(MEMBER_EX, 10_000);
        memberRepository.save(memberA);
        memberRepository.save(memberEX);

        // when
        assertThatThrownBy(() -> memberService.accountTransfer(memberA.getMemberId(), memberEX.getMemberId(), 2000))
            .isInstanceOf(IllegalStateException.class);

        // then
        Member findMemberA = memberRepository.findById(memberA.getMemberId());
        Member findMemberB = memberRepository.findById(memberEX.getMemberId());
        assertAll(
            () -> assertThat(findMemberA.getMoney()).isEqualTo(10_000),
            () -> assertThat(findMemberB.getMoney()).isEqualTo(10_000)
        );
    }

    @TestConfiguration
    static class TestConfig {
        private final DataSource dataSource;

        TestConfig(DataSource dataSource) {
            this.dataSource = dataSource;
        }

        @Bean
        MemberRepository memberRepository() {
            return new MemberRepositoryV5(dataSource);
        }

        @Bean
        MemberServiceV4 memberServiceV4() {
            return new MemberServiceV4(memberRepository());
        }
    }
}
