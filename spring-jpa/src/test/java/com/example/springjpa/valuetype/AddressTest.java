package com.example.springjpa.valuetype;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AddressTest {

    @DisplayName("동일성: 인스턴스의 참조 값을 비교, ==")
    @Test
    void identity() {
        int a = 10;
        int b = 10;

        assertThat(a).isSameAs(b);
    }

    /**
     * 값 타입 비교 (equals)
     *  - 인스턴스가 달라도 그 안에 값이 같으면 같은 것으로 봐야 함
     */
    @DisplayName("동등성: 인스턴스의 값을 비교, equals()")
    @Test
    void equivalence() {
        Address address1 = new Address("city", "street", "10000");
        Address address2 = new Address("city", "street", "10000");

        assertThat(address1).isEqualTo(address2);
    }
}
