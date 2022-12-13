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

import com.example.springjpa.inheritance.Item;

class EtcTest {

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

    @Test
    void 타입_표현() {
        String query = "select m.username, 'HELLO', true from member_jpql m where m.type = :userType";
        List<Object[]> results = em.createQuery(query)
            .setParameter("userType", MemberType.ADMIN)
            .getResultList();
        Object[] result = results.get(0);

        assertAll(
            () -> assertThat(result[0]).isEqualTo("member1"),
            () -> assertThat(result[1]).isEqualTo("HELLO"),
            () -> assertThat(result[2]).isEqualTo(true)
        );
    }

    @Test
    void 엔티티_타입_표현() {
        String query = "select i from Item i where type(i) = Book";
        List<Item> result = em.createQuery(query, Item.class)
            .getResultList();

        assertThat(result).hasSize(0);
    }
}
