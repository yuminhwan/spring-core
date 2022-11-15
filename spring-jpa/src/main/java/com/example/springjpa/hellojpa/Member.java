package com.example.springjpa.hellojpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @Entity가 붙은 클래스는 JPA가 관리, 엔티티라 한다.
 *
 * @Table은 엔티티와 매핑할 테이블 지정
 *
 * DDL 생성 기능은 DDL을 자동 생성할 때만 사용되고 JPA의 실행 로직에는 영향을 주지 않는다.
 *
 * 객체를 테이블에 맞추어 데이터 중심으로 모델링하면, 협력 관계를 만들 수 없다.
 *   - 테이블은 외래 키 조인을 사용해서 연관된 테이블을 찾는다.
 *   - 객체는 참조를 사용해서 연관된 객체를 찾는다.
 *   - 테이블과 객체 사이에는 이런 큰 간격이 있다.
 *
 *  양방향 매핑시 양쪽 다 값을 입력하지 않을 시 문제점
 *  1. 1차 캐시에 있음. -> 멤버를 넣지 않으면 멤버 정보가 없음.
 *  2. 테스트 케이스시에도 문제가 있음. -> JPA 없이도 동작해야함.
 *  3. 객체지향적으로 생각해도 양쪽 다 넣는 게 맞다. -> 순수 객체 상태 생각
 *
 *  ===> 연관관계 편의 메서드 사용 -> 상황에 맞게 한쪽에만 설정해주자!
 */
@Entity
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    @ManyToOne
    @JoinColumn(name = "TEAM_ID", insertable = false, updatable = false)
    private Team team;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 연관관계 편의 메서드
     * set 네이밍보단 다른 네이밍을 사용하는 것이 좋다.
     */
    // public void changeTeam(Team team) {
    //     this.team = team;
    //     team.getMembers().add(this);
    // }
}
