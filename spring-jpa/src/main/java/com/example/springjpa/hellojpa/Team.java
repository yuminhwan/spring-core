package com.example.springjpa.hellojpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * 객체의 양방향 관계는 사실 양방향 관계가 아니라 서로 다른 단방향 관계 2개이다.
 * 객체를 양뱡향으로 참조하려면 단방향 연관관계를 2개 만들어야 한다.
 *
 * 하지만 테이블은 외래 키 하나로 두 테이블의 연관관계를 관리한다.
 * MEMBER.TEAM_ID 외래 키 하나로 양방향 연관관계를 가진다.(양쪽으로 조인 가능)
 *
 * --> 고로, 둘 중 하나로 외래 키를 관리해야 한다. => 연관관계의 주인
 *   - 객체의 두 관계중 하나를 연관관계의 주인으로 지정
 *   - 연관관계의 주인만이 외래 키를 관리(등록, 수정)
 *   - 주인이 아닌쪽은 읽기만 가능
 *   - 주인은 mappedBy 속성 X , 주인이 아니면 mappedBy 속성으로 주인 지정
 *
 *   외래 키가 있는 곳이 주인 -> Member.team
 */
@Entity
public class Team {

    @Id
    @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;
    private String name;

    @OneToMany(mappedBy = "team")
    private List<Member> members = new ArrayList<>();

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
