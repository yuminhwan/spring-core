package com.example.springjpa.valuetype;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 임베디드 타입을 사용하기 전과 후에 매핑하는 테이블은 같다.
 * 객체와 테이블을 아주 세밀하게 매핑하는 것이 가능해짐.
 */
@Entity(name = "member_test")
@Table(name = "member_test")
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    private String username;

    @Embedded
    private Address homeAddress;

    /**
     * 값 타입을 하나 이상 저장할 때 사용
     * @ElementCollection, @CollectionTable 사용
     * 데이터베이스는 컬렉션을 같은 테이블에 저장할 수 없다.
     * 컬렉션을 저장하기 위한 별도의 테이블이 필요
     * 기본이 지연로딩
     */
    @ElementCollection
    @CollectionTable(name = "FAVORITE_FOOD", joinColumns = @JoinColumn(name = "MEMBER_ID"))
    @Column(name = "FOOD_NAME")
    private Set<String> favoriteFoods = new HashSet<>();

    /**
     * 모두 지우고 다시 저장한다...?
     *
     * 값 타입은 엔티티와 다르게 식별자 개념이 없다.
     * 값은 변경하면 추적이 어렵다.  -> 식별자가 없으니깐
     * 값 타입 컬렉션에 변경 사항이 발생하면, 주인 엔티티와 연관된 모든 데이터를 삭제하고,
     * 값 타입 컬렉션에 있는 현재 값을 모두 다시 저장한다.
     * 값 타입 컬렉션을 매핑하는 테이블은 모든 컬럼을 묶어서 기본 키를 구성해야 한다. -> null 입력 X, 중복  저장 X
     *
     * @OrderColumn으로 해결할 순 있지만 이도 문제가 많다.
     */
    // @ElementCollection
    // @CollectionTable(name = "ADDRESS", joinColumns = @JoinColumn(name = "MEMBER_ID"))
    // private List<Address> addressHistory = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "MEMBER_ID")
    private List<AddressEntity> addressHistory = new ArrayList<>();

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

    public Address getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(Address homeAddress) {
        this.homeAddress = homeAddress;
    }

    public Set<String> getFavoriteFoods() {
        return favoriteFoods;
    }

    public void setFavoriteFoods(Set<String> favoriteFoods) {
        this.favoriteFoods = favoriteFoods;
    }

    public List<AddressEntity> getAddressHistory() {
        return addressHistory;
    }
}
