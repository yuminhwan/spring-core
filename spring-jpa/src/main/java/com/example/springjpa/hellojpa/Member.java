package com.example.springjpa.hellojpa;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @Entity가 붙은 클래스는 JPA가 관리, 엔티티라 한다.
 *
 * @Table은 엔티티와 매핑할 테이블 지정
 */
@Entity
// @Table(name = "MBR")
public class Member {

    @Id
    private Long id;
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
