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

class PagingTest {

    private EntityManagerFactory emf;
    private EntityManager em;
    private EntityTransaction tx;
    private Member member;

    @BeforeEach
    void setUp() {
        emf = Persistence.createEntityManagerFactory("hello");
        em = emf.createEntityManager();

        tx = em.getTransaction();
        tx.begin();
        member = createMember(0);

        em.flush();
        em.clear();
    }

    @AfterEach
    void tearDown() {
        em.close();
        emf.close();
    }

    @Test
    void 페이징API() {
        for (int i = 1; i < 100; i++) {
            createMember(i);
        }

        List<Member> result = em.createQuery("select m from member_jpql m order by m.age desc", Member.class)
            .setFirstResult(1)
            .setMaxResults(10)
            .getResultList();

        assertAll(
            () -> assertThat(result).hasSize(10),
            () -> assertThat(result.get(0).getAge()).isEqualTo(98)
        );
    }

    private Member createMember(int age) {
        Member member = new Member();
        member.setUsername("member" + age);
        member.setAge(age);
        em.persist(member);
        return member;
    }
}
