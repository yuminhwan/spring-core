package com.example.springjpa.hellojpa;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * entityManger <- 컬렉션처럼 생각
 */
class JpaTest {

    /**
     * EntityMangerFactory는 하나만 생성해서 애플리케이션 전체에서 공유한다.
     */
    @DisplayName("설정 정보를 조회하 EntityMangerFactory를 생성한다.")
    @Test
    void createEntityManagerFactory() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        assertThat(emf).isNotNull();
    }

    /**
     * EntityManger는 쓰레드 간에 공유하면 안된다. -> (사용하고 버려야 함)
     */
    @DisplayName("EntityMangerFactory를 통해 EntityManger를 생성한다.")
    @Test
    void createEntityManger() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        em.close();
        emf.close();

        assertAll(
            () -> assertThat(emf).isNotNull(),
            () -> assertThat(em).isNotNull()
        );
    }

    @DisplayName("entityManger를 통해 persist한다.")
    @Test
    void persist() {
        // given
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        // when
        // then
        assertDoesNotThrow(() -> {
            try {
                Member member = new Member();
                member.setId(1L);
                member.setName("helloA");

                em.persist(member);

                tx.commit();
            } catch (Exception e) {
                tx.rollback();
                throw e;
            } finally {
                em.close();
            }
        });
        emf.close();
    }

    @DisplayName("entityManger를 통해 조회한다.")
    @Test
    void find() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member findMember = em.find(Member.class, 1L);
            assertAll(
                () -> assertThat(findMember.getId()).isEqualTo(1L),
                () -> assertThat(findMember.getName()).isEqualTo("helloA")
            );
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            em.close();
        }
        emf.close();
    }

    @DisplayName("더티 체킹을 통해 update 쿼리를 날린다.")
    @Test
    void dirtyChecking() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member findMember = em.find(Member.class, 1L);
            findMember.setName("helloJPA");
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            em.close();
        }
        emf.close();
    }

    /**
     * SQL를 추상화한 객체 지향 쿼리 언어 JPQL
     *
     * JPQL은 엔티티 객체를 대상으로 쿼리
     *   -> 벤더, 방언이 바뀌어도 코드 변경 X
     *   -> SQL를 추상화하였기 때문에 특정 데이터베이스 SQL에 의존 X
     *
     * SQL은 데이터베이스 테이블을 대상으로 쿼리
     */
    @DisplayName("엔티티 객체 대상의 JPQL 쿼리")
    @Test
    void jpql() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            List<Member> members = em.createQuery("select m from Member m", Member.class)
                .setFirstResult(1)
                .setMaxResults(10) // 페이지네이션
                .getResultList();

            assertThat(members).hasSize(1);
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            em.close();
        }
        emf.close();
    }
}
