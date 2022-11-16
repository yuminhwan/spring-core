package com.example.springjpa.hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * em.find -> 데이터베이스를 통해서 실제 엔티티 객체 조회
 * em.getReference() -> 데이터베이스 조회를 미루는 가짜(프록시) 엔티티 객체 조회
 *
 * 프록시 객체의 초기화
 * 1. getName()
 * 2. 영속성 컨텍스트에 초기화 요청
 * 3. DB조회
 * 4. 실제 Entity 생성
 * 5. target.getName()
 *
 * 프록시 특징
 * 1. 프록시 객체는 처음 사용할 때 한 번만 초기화
 * 2. 프록시 객체를 초기화할 때, 프록시 객체가 실제 엔티티로 바뀌는 것은 아님, 초기화되면
 *    프록시 객체를 통해서 실제 엔티티에 접근 가능 -> 교체되는 것이 아니다.
 * 3. 프록시 객체는 원본 엔티티를 상속받음. 따라서 타입 체크시 주의 필요 (==이 아닌 instance of 사용해야 함)
 * 4. 영속성 컨텍스트에 찾는 엔티티가 이미 있으면 em.getReference를 호출하더라도 실제 엔티티 반환
 * 5. 영속성 컨텍스트의 도움을 받을 수 없는 준영속 상태일 때, 프록시를 초기화하면 문제 발생
 */
public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

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
            Member m2 = em.find(Member.class, member2.getId());
            System.out.println("m1 == m2: " + (m1.getClass() == m2.getClass()));
            
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            em.close();
        }
        emf.close();
    }

    // /**
    //  * Team 정보가 필요없다.
    //  */
    // private static void printMember(Member member) {
    //     System.out.println("username = " + member.getUsername());
    // }
    //
    // /**
    //  * Team 정보가 필요하다.
    //  */
    // private static void printMemberAndTeam(Member member) {
    //     String username = member.getUsername();
    //     System.out.println("username = " + username);
    //
    //     Team team = member.getTeam();
    //     System.out.println("team.getName() = " + team.getName());
    // }
}
