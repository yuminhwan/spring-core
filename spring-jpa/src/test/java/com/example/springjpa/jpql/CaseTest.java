package com.example.springjpa.jpql;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CaseTest {

    private EntityManagerFactory emf;
    private EntityManager em;
    private EntityTransaction tx;

    @BeforeEach
    void setUp() {
        emf = Persistence.createEntityManagerFactory("hello");
        em = emf.createEntityManager();

        tx = em.getTransaction();
        tx.begin();

        Team team = new Team();
        team.setName("teamA");
        em.persist(team);

        Member member = new Member();
        member.setUsername(null);
        member.setAge(10);
        member.setType(MemberType.ADMIN);

        member.changeTeam(team);
        em.persist(member);

        em.flush();
        em.clear();
    }

    @AfterEach
    void tearDown() {
        em.close();
        emf.close();
    }

    @Test
    void 기본_CASE식() {
        String query = "select " +
            "case when m.age <= 10 then '학생요금'" +
            "     when m.age >= 60 then '경로요금'" +
            "     else '일반요금' " +
            "end " +
            "from member_jpql m";
        List<String> result = em.createQuery(query, String.class)
            .getResultList();

        assertThat(result.get(0)).isEqualTo("학생요금");
    }

    @Test
    void COALESCE는_하나씩_조회해서_null이_아니면_반환한다() {
        String query = "select coalesce(m.username, '이름 없는 회원') from member_jpql m";
        List<String> result = em.createQuery(query, String.class)
            .getResultList();

        assertThat(result.get(0)).isEqualTo("이름 없는 회원");
    }

    @Test
    void NULLIF는_두_값이_같으면_null을_반환하고_다르면_첫번째_값을_반환한다() {
        String query = "select nullif(m.username, '관리자') from member_jpql m";
        List<String> result = em.createQuery(query, String.class)
            .getResultList();

        assertThat(result.get(0)).isNull();
    }
}
