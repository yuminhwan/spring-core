package com.example.springmvc.domain.member;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberRepositoryTest {

    MemberRepository memberRepository = MemberRepository.getInstance();

    @AfterEach
    void tearDown() {
        memberRepository.clearStore();
    }

    @DisplayName("멤버를 저장한다.")
    @Test
    void save() {
        // given
        Member member = new Member("hwan", 20);

        // when
        Member savedMember = memberRepository.save(member);

        // then
        Member findMember = memberRepository.findById(savedMember.getId());
        assertThat(findMember).isEqualTo(savedMember);
    }

    @DisplayName("모든 멤버를 조회한다.")
    @Test
    void findAll() {
        // given
        Member member1 = new Member("member1", 20);
        Member member2 = new Member("member2", 30);

        memberRepository.save(member1);
        memberRepository.save(member2);

        // when
        List<Member> result = memberRepository.findAll();

        // then
        assertAll(
            () -> assertThat(result).hasSize(2),
            () -> assertThat(result).contains(member1, member2)
        );
    }
}
