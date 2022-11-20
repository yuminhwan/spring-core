package com.example.springjpa.valuetype;

import javax.persistence.Embeddable;

/**
 * 항상 값을 복사해서 사용하면 공유 참조로 인해 발생하는 부작용을 피할 수 있다.
 * 문제는 임베디드 타입처럼 직접 정의한  값 타입은 자바의 기본 타입이 아닌 객체 타입이다.
 * 객체 타입은 참조 값을 직접 대입하는 것을 막을 방법이 없다. -> 객체의 공유 참조는 피할 수 없다.
 *
 * 해결방법 -> 불변 객체
 * 1. 생성자에서 값을 모두 받고 setter 삭제
 * 2. setter를 private로
 */
@Embeddable
public class Address {

    private String city;
    private String street;
    private String zipcode;

    protected Address() {
    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    private void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    private void setStreet(String street) {
        this.street = street;
    }

    public String getZipcode() {
        return zipcode;
    }

    private void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
}
