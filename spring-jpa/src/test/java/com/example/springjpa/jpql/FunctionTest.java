package com.example.springjpa.jpql;

import static org.assertj.core.api.Assertions.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FunctionTest {

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

    // JPQL 기본 함수는 DB에 종속적이지 않는다.
    // 사용자 정의 함수는 DB에 종속적이다.
    // select 'a'  ||  'b' from member_jpql m 도 가능 (하이버네이트만 가능)
    @Test
    void concat() {
        String query = "select concat('a','b') from member_jpql m";
        String result = em.createQuery(query, String.class)
            .getSingleResult();

        assertThat(result).isEqualTo("ab");
    }

    @Test
    void locate() {
        String query = "select locate('de','abcdegf')  from member_jpql m";
        Integer result = em.createQuery(query, Integer.class)
            .getSingleResult();

        assertThat(result).isEqualTo(4);
    }

    @Test
    void size() {
        String query = "select size(t.members)  from team_jpql t";
        Integer result = em.createQuery(query, Integer.class)
            .getSingleResult();

        assertThat(result).isEqualTo(1);
    }

    // 하이버네이트는 사용전 방언에 추가해야 한다.
    //   사용하는 DB 방언을 상속받고, 사용자 정의 함수를 등록한다.
    // select group_concat(m.username) from member_jpql m 도 가능(하이버네이트만 가능)
    @Test
    void 사용자_정의_함수() {
        Member member = new Member();
        member.setUsername("member2");
        member.setAge(10);
        em.persist(member);

        String query = "select function('group_concat', m.username) from member_jpql m";
        String result = em.createQuery(query, String.class)
            .getSingleResult();

        assertThat(result).isEqualTo("member1,member2");
    }
}

