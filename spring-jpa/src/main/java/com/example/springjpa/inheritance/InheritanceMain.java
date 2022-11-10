package com.example.springjpa.inheritance;

import java.time.LocalDateTime;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class InheritanceMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Computer computer = new Computer();
            computer.setCreatedDate(LocalDateTime.now());
            computer.setCreatedBy("hwan");
            computer.setName("mac");

            em.persist(computer);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            em.close();
        }
        emf.close();
    }
}

/**
 * JOIN 전략 (@Inheritance(strategy = InheritanceType.JOINED))  <- 정석이라고 생각!
 *   - 장점
 *        - 테이블 정규화
 *        - 외래 키 참조 무결성 제약조건 활용가능
 *        - 저장공간 효율화
 *   - 단점
 *        - 조회시 조인을 많이 사용, 성능 저하
 *        - 조회 쿼리가 복잡함
 *        - 데이터 저장 시 INSERT 쿼리 두번 호출
 *
 *     create table Item (
 *        DTYPE varchar(31) not null,
 *         id bigint not null,
 *         name varchar(255),
 *         price integer not null,
 *         primary key (id)
 *     )
 *
 *     create table Book (
 *        author varchar(255),
 *         isbn varchar(255),
 *         id bigint not null,
 *         primary key (id)
 *     )
 *
 *
 *        into
 *             Item
 *             (name, price, DTYPE, id)
 *         values
 *             (?, ?, 'M', ?)
 *
 *         into
 *             Movie
 *             (actor, director, id)
 *         values
 *             (?, ?, ?)
 *
 *     select
 *         movie0_.id as id1_2_0_,
 *         movie0_1_.name as name2_2_0_,
 *         movie0_1_.price as price3_2_0_,
 *         movie0_.actor as actor1_5_0_,
 *         movie0_.director as director2_5_0_
 *     from
 *         Movie movie0_
 *     inner join
 *         Item movie0_1_
 *             on movie0_.id=movie0_1_.id
 *     where
 *         movie0_.id=?
 *
 *   SINGLE_TABLE 전략 (@Inheritance(strategy = InheritanceType.SINGLE_TABLE)) <- 단순할 때?
 *     - 장점
 *         - 조인이 필요 없으므로 일반적으로 조회 성능이 빠름
 *         - 조회 쿼리가 단순함
 *     - 단점
 *         - 자식 엔티티가 매핑한 컬럼은 모두 null 허용
 *         - 단일 테이블에 모든 것을 저장하므로 테이블이 커질 수 있어 상황에 따라서 조회 성능이 오히려 느려질 수 있다.
 *
 *   create table Item (
 *        DTYPE varchar(31) not null,
 *         id bigint not null,
 *         name varchar(255),
 *         price integer not null,
 *         actor varchar(255),
 *         director varchar(255),
 *         artist varchar(255),
 *         author varchar(255),
 *         isbn varchar(255),
 *         primary key (id)
 *     )
 *
 *
 *         into
 *             Item
 *             (name, price, actor, director, DTYPE, id)
 *         values
 *             (?, ?, ?, ?, 'M', ?)
 *
 *
 *      select
 *         movie0_.id as id2_0_0_,
 *         movie0_.name as name3_0_0_,
 *         movie0_.price as price4_0_0_,
 *         movie0_.actor as actor5_0_0_,
 *         movie0_.director as director6_0_0_
 *     from
 *         Item movie0_
 *     where
 *         movie0_.id=?
 *         and movie0_.DTYPE='M'
 *
 *
 *  @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)  <- 쓰지 말자 - 데이터베이스 설계자와 ORM 전문가 둘 다 추천 XX
 *      - 장점
 *         - 서브 타입을 명확하게 구분해서 처리할 때 효과적
 *         - not null 제약조건 사용 가능
 *      - 단점
 *         - 여러 자식 테이블을 함께 조회할 때 성능이 느림(UNION SQL)
 *         - 자식 테이블을 통합해서 쿼리하기 어려움
 *
 *     create table Book (
 *        id bigint not null,
 *         name varchar(255),
 *         price integer not null,
 *         author varchar(255),
 *         isbn varchar(255),
 *         primary key (id)
 *     )
 *
 *
 *         into
 *             Movie
 *             (name, price, actor, director, id)
 *         values
 *             (?, ?, ?, ?, ?)
 *
 *             select
 *         movie0_.id as id1_2_0_,
 *         movie0_.name as name2_2_0_,
 *         movie0_.price as price3_2_0_,
 *         movie0_.actor as actor1_5_0_,
 *         movie0_.director as director2_5_0_
 *     from
 *         Movie movie0_
 *     where
 *         movie0_.id=?
 *
 *
 *   상위 클래스로 조회하면? -> union 모든 테이블 돌아봐야함 -> 비효율
 * select
 *         item0_.id as id1_2_0_,
 *         item0_.name as name2_2_0_,
 *         item0_.price as price3_2_0_,
 *         item0_.actor as actor1_5_0_,
 *         item0_.director as director2_5_0_,
 *         item0_.artist as artist1_0_0_,
 *         item0_.author as author1_1_0_,
 *         item0_.isbn as isbn2_1_0_,
 *         item0_.clazz_ as clazz_0_
 *     from
 *         ( select
 *             id,
 *             name,
 *             price,
 *             actor,
 *             director,
 *             null as artist,
 *             null as author,
 *             null as isbn,
 *             1 as clazz_
 *         from
 *             Movie
 *         union
 *         all select
 *             id,
 *             name,
 *             price,
 *             null as actor,
 *             null as director,
 *             artist,
 *             null as author,
 *             null as isbn,
 *             2 as clazz_
 *         from
 *             Album
 *         union
 *         all select
 *             id,
 *             name,
 *             price,
 *             null as actor,
 *             null as director,
 *             null as artist,
 *             author,
 *             isbn,
 *             3 as clazz_
 *         from
 *             Book
 *     ) item0_
 * where
 *     item0_.id=?
 */
