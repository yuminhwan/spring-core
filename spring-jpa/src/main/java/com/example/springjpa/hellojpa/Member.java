package com.example.springjpa.hellojpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @Entity가 붙은 클래스는 JPA가 관리, 엔티티라 한다.
 *
 * @Table은 엔티티와 매핑할 테이블 지정
 *
 * DDL 생성 기능은 DDL을 자동 생성할 때만 사용되고 JPA의 실행 로직에는 영향을 주지 않는다.
 */
@Entity
// @Table(uniqueConstraints = {@UniqueConstraint(name = "NAME_AGE_UNIQUE",
//                                               columnNames = {"NAME,AGE"})}
// )
public class Member {

    @Id
    private Long id;

    @Column(unique = true, length = 10)
    private String name;

    protected Member() {
    }

    public Member(Long id, String name) {
        this.id = id;
        this.name = name;
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
