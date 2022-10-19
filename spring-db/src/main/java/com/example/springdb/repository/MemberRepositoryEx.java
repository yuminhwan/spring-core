package com.example.springdb.repository;

import java.sql.SQLException;

import com.example.springdb.domain.Member;

/**
 * 체크 예외 문제점!
 * 특정 기술에 종속적인 인터페이스
 * 유연하지 못함. -> 변경이 어렵다.
 */
public interface MemberRepositoryEx {
    Member save(Member member) throws SQLException;

    Member findById(String memberId) throws SQLException;

    void update(String memberId, int money) throws SQLException;

    void delete(String memberId) throws SQLException;
}
