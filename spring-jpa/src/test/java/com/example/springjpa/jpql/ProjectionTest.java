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

import com.example.springjpa.valuetype.Address;

class ProjectionTest {

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

        em.flush();
        em.clear();
    }

    @AfterEach
    void tearDown() {
        em.close();
        emf.close();
    }

    @Test
    void 엔티티_프로젝션은_영속성_컨텍스트에서_관리한다() {
        List<Member> result = em.createQuery("select m from member_jpql m", Member.class)
            .getResultList();

        Member findMember = result.get(0);
        findMember.setAge(20);
        em.flush();
        em.clear();

        Member updateMember = em.createQuery("select m from member_jpql m where m.id = :id", Member.class)
            .setParameter("id", member.getId())
            .getSingleResult();
        assertThat(updateMember.getAge()).isEqualTo(20);
    }

    /**
     * SQL문에 조인절이 나오지 않기 때문에 지양하자.
     */
    @Test
    void 엔티티_프로젝션_연관_엔티티는_조인_쿼리가_나간다() {
        List<Team> result = em.createQuery("select m.team from member_jpql m", Team.class)
            .getResultList();

        assertThat(result).hasSize(0);
    }

    @Test
    void 임베디드_타입_프로젝션() {
        List<Address> result = em.createQuery("select o.address from Order o", Address.class)
            .getResultList();

        assertThat(result).hasSize(0);
    }

    /**
     * 어떻게 조회하지...?
     */
    @Test
    void 스칼라_타입_프로젝션() {
        em.createQuery("select distinct m.username, m.age from member_jpql m")
            .getResultList();
    }

    @Test
    void Query타입으로_조회() {
        List resultList = em.createQuery("select distinct m.username, m.age from member_jpql m")
            .getResultList();

        Object o = resultList.get(0);
        Object[] result = (Object[])o;

        assertAll(
            () -> assertThat(result[0]).isEqualTo("member1"),
            () -> assertThat(result[1]).isEqualTo(10)
        );
    }

    @Test
    void Object배열타입으로_조회() {
        List<Object[]> resultList = em.createQuery("select distinct m.username, m.age from member_jpql m")
            .getResultList();
        Object[] result = resultList.get(0);

        assertAll(
            () -> assertThat(result[0]).isEqualTo("member1"),
            () -> assertThat(result[1]).isEqualTo(10)
        );
    }

    /**
     * 패키지 명을 포함한 전체 클래스 명 입력
     * 순서와 타입이 일치하는 생성자 필요
     */
    @Test
    void new명령어로_조회() {
        List<MemberDto> resultList = em.createQuery(
                "select new com.example.springjpa.jpql.MemberDto(m.username, m.age) from member_jpql m",
                MemberDto.class)
            .getResultList();

        MemberDto result = resultList.get(0);

        assertAll(
            () -> assertThat(result.getUsername()).isEqualTo("member1"),
            () -> assertThat(result.getAge()).isEqualTo(10)
        );
    }

    private Member createMember(String name) {
        Member member = new Member();
        member.setUsername(name);
        member.setAge(10);
        em.persist(member);
        return member;
    }
}
