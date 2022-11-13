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
 */
@Entity
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    // @Column(name = "TEAM_ID")
    // private Long teamId;

    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
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

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
