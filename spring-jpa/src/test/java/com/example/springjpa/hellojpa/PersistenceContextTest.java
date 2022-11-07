package com.example.springjpa.hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PersistenceContextTest {

    private EntityManagerFactory emf;
    private EntityManager em;

    @BeforeEach
    void setUp() {
        emf = Persistence.createEntityManagerFactory("hello");
        em = emf.createEntityManager();
    }

    @AfterEach
    void tearDown() {
        emf.close();
    }

    /**
     * 영속 상태라고 바로 쿼리가 날라가는 것이 아닌 트랜잭션 커밋시점에 쿼리가 날라간다.
     */
    @DisplayName("비영속 -> 영속 : 영속성 컨텍스트에서 관리되는 상태")
    @Test
    void persist() {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            // 비영속
            Member member = new Member();
            member.setId(100L);
            member.setName("HelloJPA");

            // 영속
            System.out.println("BEFORE");
            em.persist(member);
            System.out.println("AFTER");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

    @DisplayName("영속 -> 준영속 : 영속성 컨텍스트에서 분리")
    @Test
    void detach() {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            // 비영속
            Member member = new Member();
            member.setId(101L);
            member.setName("HelloJPA");

            // 영속
            em.persist(member);

            // 준영속
            em.detach(member);

            // 삭제 : 실제 DB에 삭제 요청
            em.remove(member);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }
}
