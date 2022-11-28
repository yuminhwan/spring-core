package com.example.springjpa.jpql;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JpqlTest {

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
        member = createMember("member1");
    }

    @AfterEach
    void tearDown() {
        em.close();
        emf.close();
    }

    @Test
    void 반환_타입이_명확할_때_TypeQuery() {
        try {
            TypedQuery<Member> query1 = em.createQuery("select m from member_jpql m", Member.class);
            TypedQuery<String> query2 = em.createQuery("select m.username from member_jpql m", String.class);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

    @Test
    void 반환_타입이_명확하지_않을_때_Query() {
        try {
            Query query3 = em.createQuery("select m.username, m.age from member_jpql m");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

    @Test
    void 결과가_하나_이상일_때_리스트_반환_getResultList() {
        try {
            TypedQuery<Member> query = em.createQuery("select m from member_jpql m", Member.class);
            List<Member> resultList = query.getResultList();

            assertThat(resultList).hasSize(1);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

    @Test
    void getResultList_결과가_없다면_빈_리스트_반환() {
        try {
            TypedQuery<Order> query = em.createQuery("select o from Order o", Order.class);
            List<Order> resultList = query.getResultList();

            assertThat(resultList).isEmpty();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

    @Test
    void 결과가_정확히_하나_일_때_단일_객체_반환_getSingleResult() {
        try {
            TypedQuery<Member> query = em.createQuery("select m from member_jpql m where m.id = 1", Member.class);
            Member result = query.getSingleResult();

            assertThat(result.getId()).isGreaterThan(0);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

    @Test
    void getSingleResult_결과_값이_없으면_NoResultException_예외_발생() {
        try {
            TypedQuery<Member> query = em.createQuery("select m from member_jpql m where m.id = 100", Member.class);

            assertThatThrownBy(() -> query.getSingleResult())
                .isInstanceOf(NoResultException.class);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

    @Test
    void getSingleResult_결과_값이_둘_이상이면_NonUniqueResultException_예외_발생() {
        try {
            createMember("member2");
            TypedQuery<Member> query = em.createQuery("select m from member_jpql m", Member.class);

            assertThatThrownBy(() -> query.getSingleResult())
                .isInstanceOf(NonUniqueResultException.class);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

    /**
     * 위치기반도 가능하지만 가능한 쓰지 말기
     * ?1 -> (1,usernameParam)
     */
    @Test
    void 파라미터_바인딩() {
        try {
            Member result = em.createQuery("select m from member_jpql m where m.username = :username",
                    Member.class)
                .setParameter("username", "member1")
                .getSingleResult();

            assertThat(result.getUsername()).isEqualTo("member1");
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

    private Member createMember(String name) {
        Member member = new Member();
        member.setUsername(name);
        member.setAge(10);
        em.persist(member);
        return member;
    }
}
