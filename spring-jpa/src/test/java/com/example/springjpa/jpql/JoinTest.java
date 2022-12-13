package com.example.springjpa.jpql;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JoinTest {

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
        member.setUsername("member1");
        member.setAge(10);
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
    void 이너조인() {
        String query = "select m from member_jpql m inner join m.team";
        List<Member> result = em.createQuery(query, Member.class)
            .getResultList();

        assertAll(
            () -> assertThat(result).hasSize(1),
            () -> assertThat(result.get(0).getTeam().getName()).isEqualTo("teamA")
        );
    }

    @Test
    void 레프트_조인() {
        String query = "select m from member_jpql m left join m.team";
        List<Member> result = em.createQuery(query, Member.class)
            .getResultList();

        assertAll(
            () -> assertThat(result).hasSize(1),
            () -> assertThat(result.get(0).getTeam().getName()).isEqualTo("teamA")
        );
    }

    /* select
    m
from
    member_jpql m,
    Team t
where
    m.username = t.name */
    @Test
    void 세타_조인() {
        String query = "select m from member_jpql m, Team t where m.username = t.name";
        List<Member> result = em.createQuery(query, Member.class)
            .getResultList();

        assertThat(result).hasSize(0);
    }

    @Test
    void on절_조인_대상_필터링() {
        String query = "select m from member_jpql m left join m.team t on t.name = 'teamA'";
        List<Member> result = em.createQuery(query, Member.class)
            .getResultList();

        assertThat(result).hasSize(1);
    }

    /* select
       m
   from
       member_jpql m
   left join
       Team t
           on t.name = m.username */
    @Test
    void on절_연관관계_없는_엔티티_외부_조인() {
        String query = "select m from member_jpql m left join Team t on t.name = m.username";
        List<Member> result = em.createQuery(query, Member.class)
            .getResultList();

        assertThat(result).hasSize(1);
    }
}
