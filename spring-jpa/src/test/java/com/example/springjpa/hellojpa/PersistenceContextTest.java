package com.example.springjpa.hellojpa;

import static org.assertj.core.api.Assertions.*;

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

    @DisplayName("해당 ID값이 1차 캐시에 있으면 1차 캐시에서 조회한다.")
    @Test
    void firstLevelCache() {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member = new Member();
            member.setId(102L);
            member.setName("HelloJPA");

            // 영속
            em.persist(member);

            Member findMember = em.find(Member.class, 102L);

            System.out.println("findMember.getId() = " + findMember.getId());
            System.out.println("findMember.getName() = " + findMember.getName());

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

    /**
     * 영속 엔티티의 동일성 보장
     *      1차 캐시로 REPEATABLE READ 등급의 트랜잭션 격리 수준을
     *      데이터베이스가 아닌 애플리케이션 차원에서 제공
     */
    @DisplayName("DB에 엔티티 조회시 1차 캐시에 저장한다.")
    @Test
    void findThenSaveFirstLevelCache() {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member findMember1 = em.find(Member.class, 102L);
            Member findMember2 = em.find(Member.class, 102L);

            assertThat(findMember1).isSameAs(findMember2);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

    /**
     * persist시에 쿼리가 나가는 것이 아닌 쓰기 지연 저장소에
     * 저장해놓고 트랜잭션이 커밋될 시 flush 즉, 쓰기 지연 저장소 쿼리가 나가게 되고
     * 실제 DB에 커밋이 된다.
     *   -> 버퍼링 : batchSize를 통해 한번의 통신으로 한번에 쿼리를 반영할 수 있다.
     */
    @DisplayName("트랜잭션을 지원하는 쓰기 지연")
    @Test
    void lazyWriting() {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member1 = new Member(170L, "A");
            Member member2 = new Member(180L, "B");

            em.persist(member1);
            em.persist(member2);
            System.out.println("======================");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

    /**
     * 자바 컬렉션을 사용하는 것처럼 해야 함 -> 해당 객체 변경 시 실제DB도 변경되야함!
     *   -> 변경 시 update 쿼리 나감
     *   -> 커밋 시 flush 진행 -> 엔티티와 스냅샷 비교 -> UPDATE SQL을 쓰기 지연 SQL저장소에 생성 -> flush (DB SQL 반영) -> DB 커밋
     *   스냅샷 : 영속성 컨텍스트에 들어온 최초 시점을 복사해놓는다.
     *   플러시 : 영속성 컨텍스트의 변경내용을 데이터베이스에 반영
     */
    @DisplayName("변경 감지를 통해 엔티티를 수정할 수 있다.")
    @Test
    void dirtyChecking() {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member = em.find(Member.class, 150L);
            member.setName("ZZZZZ");

            System.out.println("======================");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

    /**
     * 1. em.flush() - 직접 호출
     * 2. 트랜잭션 커밋 - 플러시 자동 호출
     * 3. JPQL 쿼리 실행 - 플러시 자동 호출
     *      -> member1,2,3을 persist 한 뒤 JPQL쿼리 실행 시 바로 DB에 찌르는 데 아직 반영이 안되어있어 일관성을 보장못함.
     *      -> 이를 해결하고자 JPQL 쿼리 실행 시 플러시가 자동으로 호출됨 -> (관련된 객체들에 대한 JPQL일 시) <- 확인필요
     *      -> FlushModeType.AUTO(default) : 커밋이나 쿼리를 실행할 때 플러시
     *      -> FlushModeType.COMMIT : 커밋할 때만 플러시
     *
     * 1차 캐시를 지우는 것이 아닌 영속성 컨텍스트 쓰기 지연 SQL 저장소를 보내 DB에 반영하는 것
     * 영속성 컨텍스트의 변경내용을 데이터베이스에 동기화
     * 트랜잭션이라는 작업 단위가 중요 -> 커밋 직전에만 동기화하면 됨
     */
    @DisplayName("영속성 컨텍스트 플러시 3가지 방식")
    @Test
    void flush() {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member = new Member(200L, "member200");
            em.persist(member);

            em.flush();

            System.out.println("======================");
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }
}
