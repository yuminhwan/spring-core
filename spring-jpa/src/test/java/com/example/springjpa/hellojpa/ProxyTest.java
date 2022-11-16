package com.example.springjpa.hellojpa;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnitUtil;

import org.hibernate.Hibernate;
import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ProxyTest {

    private EntityManagerFactory emf;
    private EntityManager em;

    @BeforeEach
    void setUp() {
        emf = Persistence.createEntityManagerFactory("hello");
        em = emf.createEntityManager();
    }

    @AfterEach
    void tearDown() {
        em.close();
        emf.close();
    }

    @DisplayName("프록시와 == 비교하면 안된다.")
    @Test
    void proxy() {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member1 = new Member();
            member1.setUsername("member1");
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("member1");
            em.persist(member2);

            em.flush();
            em.clear();

            Member m1 = em.find(Member.class, member1.getId());
            Member m2 = em.getReference(Member.class, member2.getId());

            assertThat(m1.getClass() == m2.getClass()).isFalse();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

    @DisplayName("프록시 객체와 비교시 instanceOf를 사용한다.")
    @Test
    void instanceOf() {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member1 = new Member();
            member1.setUsername("member1");
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("member1");
            em.persist(member2);

            em.flush();
            em.clear();

            Member m1 = em.find(Member.class, member1.getId());
            Member m2 = em.getReference(Member.class, member2.getId());

            assertAll(
                () -> assertThat(m1 instanceof Member).isTrue(),
                () -> assertThat(m2 instanceof Member).isTrue()
            );
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

    /**
     * 한 트랜잭션 안에서 == 비교시 항상 같아야 한다.
     */
    @DisplayName("영속성 컨텍스트에 찾는 엔티티가 이미 있으면 실제 객체를 반환한다.")
    @Test
    void returnNotProxyInstance() {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member1 = new Member();
            member1.setUsername("member1");
            em.persist(member1);

            em.flush();
            em.clear();

            Member m1 = em.find(Member.class, member1.getId());
            Member reference = em.getReference(Member.class, member1.getId());

            assertAll(
                () -> assertThat(reference.getClass()).isEqualTo(Member.class),
                () -> assertThat(m1).isEqualTo(reference)
            );
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

    @DisplayName("getReference시 영속성 컨텍스트에 없다면 Proxy 객체를 항상 반환한다.")
    @Test
    void getReferenceReturnProxyInstance() {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member1 = new Member();
            member1.setUsername("member1");
            em.persist(member1);

            em.flush();
            em.clear();

            Member m1 = em.getReference(Member.class, member1.getId());
            Member reference = em.getReference(Member.class, member1.getId());

            assertAll(
                () -> assertThat(m1.getClass()).isEqualTo(reference.getClass()),
                () -> assertThat(m1).isEqualTo(reference)
            );
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

    /**
     * == 비교를 맞추기 위해서.
     */
    @DisplayName("getReference 한 이후 find를 하면 프록시 객체를 반환한다.")
    @Test
    void ReturnProxy() {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member1 = new Member();
            member1.setUsername("member1");
            em.persist(member1);

            em.flush();
            em.clear();

            Member refMember = em.getReference(Member.class, member1.getId());
            Member findMember = em.find(Member.class, member1.getId());

            assertAll(
                () -> assertThat(refMember.getClass()).isEqualTo(findMember.getClass()),
                () -> assertThat(refMember).isEqualTo(findMember)
            );
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

    @DisplayName("영속성 컨텍스트의 도움을 받을 수 없는 준영속 상태일 때, 프록시를 초기화하면 문제가 발생한다.")
    @Test
    void throwException() {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member1 = new Member();
            member1.setUsername("member1");
            em.persist(member1);

            em.flush();
            em.clear();

            Member refMember = em.getReference(Member.class, member1.getId());

            // em.detach(refMember);
            // em.close();
            em.clear();

            assertThatThrownBy(() -> System.out.println(refMember.getName()))
                .isInstanceOf(LazyInitializationException.class);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

    @DisplayName("프록시 인스턴스의 초기화 여부를 확인한다.")
    @Test
    void isLoaded() {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member1 = new Member();
            member1.setUsername("member1");
            em.persist(member1);

            em.flush();
            em.clear();

            Member refMember = em.getReference(Member.class, member1.getId());
            PersistenceUnitUtil persistenceUnitUtil = emf.getPersistenceUnitUtil();
            assertThat(persistenceUnitUtil.isLoaded(refMember)).isFalse();

            // refMember.getName(); // 강제 초기화
            Hibernate.initialize(refMember); // 강제 초기화
            assertThat(persistenceUnitUtil.isLoaded(refMember)).isTrue();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }
}
