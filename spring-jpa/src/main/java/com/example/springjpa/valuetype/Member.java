package com.example.springjpa.valuetype;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "member_test")
@Table(name = "member_test")
public class Member {
    @Id
    @GeneratedValue
    private Long id;

    private String username;

    /**
     * 임베디드 타입을 사용하기 전과 후에 매핑하는 테이블은 같다.
     * 객체와 테이블을 아주 세밀하게 매핑하는 것이 가능해짐.
     */
    @Embedded
    private Period workPeriod;

    @Embedded
    private Address homeAddress;

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

    public Period getWorkPeriod() {
        return workPeriod;
    }

    public void setWorkPeriod(Period workPeriod) {
        this.workPeriod = workPeriod;
    }

    public Address getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(Address homeAddress) {
        this.homeAddress = homeAddress;
    }

}
