package com.example.springdb.repository;

import static org.assertj.core.api.Assertions.*;

import java.sql.SQLException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.springdb.domain.Member;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class MemberRepositoryV0Test {

    MemberRepositoryV0 repository = new MemberRepositoryV0();

    @DisplayName("회원 crud 성공")
    @Test
    void crud() throws SQLException {
        //save
        Member member = new Member("memberV0", 10_000);
        repository.save(member);

        //findById
        Member findMember = repository.findById(member.getMemberId());
        log.info("findMember={}", findMember);
        assertThat(findMember).isEqualTo(member);
    }

}
