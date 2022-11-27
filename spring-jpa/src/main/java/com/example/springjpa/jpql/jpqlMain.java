package com.example.springjpa.jpql;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.example.springjpa.hellojpa.Member;

public class jpqlMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Member> query = cb.createQuery(Member.class);

            Root<Member> m = query.from(Member.class);

            CriteriaQuery<Member> cq = query.select(m);

            String username = "kim";
            if (username != null) {
                cq = cq.where(cb.equal(m.get("username"), "kim"));
            }
            List<Member> resultList = em.createQuery(cq)
                .getResultList();

            Member member = new Member();
            member.setUsername("member1");
            em.persist(member);

            // flush -> commit , query

            List<Member> result = em.createNativeQuery("select * from Member", Member.class)
                .getResultList();

            // dbconn.executeQuery("select * from member"); -> flush X -> 결과 0

            em.flush();

            System.out.println("======================");
            for (Member member1 : result) {
                System.out.println("member1 = " + member1);
            }
            System.out.println("======================");

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
