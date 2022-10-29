package com.example.java8to11.interfacechange;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FooTest {

    @DisplayName("이름을 반환한다.")
    @Test
    void returnName() {
        String name = "hwan";
        Foo foo = new DefaultFoo(name);

        String sut = foo.returnName();

        assertThat(sut).isEqualTo(name);
    }

    @DisplayName("이름을 대문자로 반환한다.")
    @Test
    void returnNameUpperCase() {
        String name = "hwan";
        Foo foo = new DefaultFoo(name);

        String sut = foo.returnNameUpperCase();

        assertThat(sut).isEqualTo("HWAN");
    }

    @DisplayName("스태틱 메서드")
    @Test
    void staticMethod() {
        // given
        // when
        String sut = Foo.returnAnyting();

        // then
        assertThat(sut).isEqualTo("any");
    }
}
