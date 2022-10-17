package com.example.springdb.repository;

import static org.assertj.core.api.Assertions.*;

import java.sql.SQLException;
import java.util.NoSuchElementException;

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
        Member member = new Member("memberV100", 10_000);
        repository.save(member);

        //findById
        Member findMember = repository.findById(member.getMemberId());
        log.info("findMember={}", findMember);
        assertThat(findMember).isEqualTo(member);

        //update : money : 10000 -> 20000
        repository.update(member.getMemberId(), 20_000);
        Member updateMember = repository.findById(member.getMemberId());
        assertThat(updateMember.getMoney()).isEqualTo(20_000);

        //delete
        repository.delete(member.getMemberId());
        assertThatThrownBy(() -> repository.findById(member.getMemberId()))
            .isInstanceOf(NoSuchElementException.class);
    }

}
