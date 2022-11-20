package com.example.springjpa.valuetype;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 실무에서는 상황에 따라 값 타입 컬렉션 대신에 일대다 관계를 고려
 * 일대다 관계를 위한 엔티티를 만들고, 여기에서 값 타입을 사용
 * 영속성 전이 + 고아 객체 제거를 사용해서 값 타입 컬렉션처럼 사용
 *
 * 간단할 때 사용!
 *
 * 식별자가 필요하고, 지속해서 값을 추적, 변경해야 한다면? -> 값 타입이 아닌 엔티티!
 */
@Entity
@Table(name = "ADDRESS")
public class AddressEntity {

    @Id
    @GeneratedValue
    private Long id;

    private Address address;

    protected AddressEntity() {
    }

    public AddressEntity(String city, String street, String zipcode) {
        this.address = new Address(city, street, zipcode);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
