package com.example.springdb.repository;

import java.sql.SQLException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.springdb.domain.Member;

class MemberRepositoryV0Test {

    MemberRepositoryV0 repository = new MemberRepositoryV0();

    @DisplayName("회원 crud 성공")
    @Test
    void crud() throws SQLException {
        Member member = new Member("memberV0", 10_000);
        repository.save(member);
    }

}
