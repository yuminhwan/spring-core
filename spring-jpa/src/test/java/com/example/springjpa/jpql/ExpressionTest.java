package com.example.springjpa.jpql;

import static org.assertj.core.api.Assertions.*;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * 경로 표현식
 *    - 상태 필드 : 단순히 값을 저장하기 위한 필드 (m.username)
 *    - 연관 필드 : 연관관계를 위한 필드
 *       - 단일 값 연관 필드 : @ManyToOne, @OneToOne, 대상이 엔티티 (m.team)
 *       - 컬렉션 값 연관 필드 : @OneToMany, @ManyToMany, 대상이 컬렉션 (m.orders)
 *
 * 특징
 *     - 상태 필드 : 경로 탐색의 끝, 더 이상 탐색 불가
 *     - 단일 값 연관 경로 : 묵시적 내부 조인(inner join) 발생, 탐색 O
 *     - 컬렉션 값 연관 경로 : 묵시적 내부 조인 발생, 탐색X
 *       - FROM 절에서 명시적 조인을 통해 별칭을 얻으면 별칭을 통해 탐색 가능
 *     => 묵시적 조인 쓰지말고 명시적 조인을 쓰자!!!
 *
 * 명시적 조인 : join 키워드 직접 사용
 *    - select m from Member m join m.team t
 * 묵시적 조인 : 경로 표현식에 의해 묵시적으로 SQL 조인 발생 (내부 조인만 가능)
 *    - select m.team from Member m
 *
 *  경로 탐색은 주로 SELECT, WHERE 절에서 사용하지만 묵시적 조인으로 인해 SQL의 FROM (JOIN) 절에 영향을 준다.
 *
 *  그러니 가급적 묵시적 조인 대신에 명시적 조인 사용
 *    왜? 조인은 SQL 튜닝에 중요 포인트이다. 묵시적 조인이 일어나는 상황을 한눈에 파악하기 어렵기 때문에
 */
class ExpressionTest {

    private EntityManagerFactory emf;
    private EntityManager em;

    @BeforeEach
    void setUp() {
        emf = Persistence.createEntityManagerFactory("hello");
        em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
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
    void 상태_필드는_더_이상_탐색이_불가능하다() {
        String query = "select m.username from member_jpql m";
        String result = em.createQuery(query, String.class)
            .getSingleResult();

        assertThat(result).isEqualTo("member1");
    }

    /* select
            team1_.TEAM_ID as team_id1_19_,
            team1_.name as name2_19_
        from
            member_jpql member0_
        inner join
            team_jpql team1_
                on member0_.TEAM_ID=team1_.TEAM_ID */
    // 묵시적 내부 조인은 피해야 한다! -> 직관적이지 않다. -> 성능 튜닝이 어렵다. -> 운영 어려움
    @Test
    void 단일_값_연관_경로는_묵시적_내부_조인이_발생한다() {
        String query = "select m.team from member_jpql m";
        Team result = em.createQuery(query, Team.class)
            .getSingleResult();

        assertThat(result.getName()).isEqualTo("teamA");
    }

    /* elect
            team1_.name as col_0_0_
        from
            member_jpql member0_ cross
        join
            team_jpql team1_
        where
            member0_.TEAM_ID=team1_.TEAM_ID */
    // 애는 cross join이 나가네?
    @Test
    void 단일_값_연관_경로는_탐색이_가능하다() {
        String query = "select m.team.name from member_jpql m";
        String result = em.createQuery(query, String.class)
            .getSingleResult();

        assertThat(result).isEqualTo("teamA");
    }

    /* select
            members1_.id as id1_9_,
            members1_.age as age2_9_,
            members1_.TEAM_ID as team_id5_9_,
            members1_.type as type3_9_,
            members1_.username as username4_9_
        from
            team_jpql team0_
        inner join
            member_jpql members1_
                on team0_.TEAM_ID=members1_.TEAM_ID */
    @Test
    void 컬렉션_값_연관_경로는_묵시적_내부_조인이_발생한다() {
        String query = "select t.members from team_jpql t";
        Collection result = em.createQuery(query, Collection.class)
            .getResultList();

        assertThat(result).hasSize(1);
    }

    // size는 가능
    @Test
    void 컬렉션_값_연관_경로는_탐색이_불가능하다() {
        String query = "select t.members.size from team_jpql t";
        Integer result = em.createQuery(query, Integer.class)
            .getSingleResult();

        assertThat(result).isEqualTo(1);
    }

    @Test
    void 컬렉션_값_연관_경로는_FROM절에서_명시적_조인을_통해_별칭을_얻어_탐색이_가능하다() {
        String query = "select m.username from team_jpql t join t.members m";
        String result = em.createQuery(query, String.class)
            .getSingleResult();

        assertThat(result).isEqualTo("member1");
    }
}
