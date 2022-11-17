package com.example.springjpa.cascade;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/*
 *  Parent만 저장해도 child도 함께 저장되면 좋겠다! -> Cascade
 *
 *  영속성 전이
 *   - 특정 엔티티를 영속 상태로 만들 때 연관된 엔티티도 함께 영속상태로 만들고 싶을 때
 *   - 연관관계를 매핑하는 것과 아무 관련이 없음
 *   - 엔티티를 영속화할 때 연관된 엔티티도 함께 영속화하는 편리함을 제공할
 *   - 소유주가 하나일 때 -> 다른 곳에서도 관리한다면 쓰면 안된다!
 *
 *  고아 객체
 *   - 고아 객체 제거 : 부모 엔티티와 연관관계가 끊어진 자식 엔티티를 자동으로 삭제
 *   - 참조가 제거된 엔티티는 다른 곳에서 참조되지 않는 고아 객체로 보고 삭제하는 기능
 *   - 참조하는 곳이 하나일 때 & 특정 엔티티가 개인 소유할 때 사용
 *   - @OneToOne, @OneToMany만 가능
 *   - 추가로 부모 제거시 자식은 고아가 됨. 즉, CasCadeType.REMOVE처럼 동작
 *
 *  영속성 전이 + 고아 객체, 생명주기
 *   - CasecadeType.ALL + orphanRemoval=true
 *   - 스스로 생명주기를 관리하는 엔티티는 em.persist로 영속화, em.remove로 제거
 *   - 두 옵션을 모두 활성화하면 부모 엔티티를 통해서 자식의 생명주기를 관리할 수 있다.
 *   - DDD의 Aggregate Root개념을 구현할 때 유용
 */
public class CascadeMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Child child1 = new Child();
            Child child2 = new Child();

            Parent parent = new Parent();
            parent.addChild(child1);
            parent.addChild(child2);

            em.persist(parent);
            em.persist(child1);
            em.persist(child2);

            em.flush();
            em.clear();

            Parent findParent = em.find(Parent.class, parent.getId());
            // findParent.getChildren().remove(0); // delete 쿼리

            em.remove(findParent);

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
