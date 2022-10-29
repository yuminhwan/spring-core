package com.example.java8to11;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MethodReferenceTest {

    @DisplayName("스태틱 메서드 참조 -> 타입::스태틱 메서드")
    @Test
    void staticMethodReference() {
        UnaryOperator<String> hi = Greeting::hi; // (str) -> "hi" + str 와 동일

        String sut = hi.apply("hwan");

        assertThat(sut).isEqualTo("hihwan");
    }

    @DisplayName("특정 객체의 인스턴스 메서드 참조 -> 객체 래퍼런스::인스턴스 메서드")
    @Test
    void instanceMethodReferenceTest() {
        Greeting greeting = new Greeting();
        UnaryOperator<String> hi = greeting::hello;

        String sut = hi.apply("hwan");

        assertThat(sut).isEqualTo("hellohwan");
    }

    @DisplayName("생성자 참조 -> 타입::new")
    @Test
    void constructorReferenceTest() {
        Supplier<Greeting> newGreeting = Greeting::new;

        Greeting greeting = newGreeting.get();

        assertThat(greeting).isNotNull();
    }

    /**
     * 위의 Supplier와는 다른 생성자 참조
     */
    @DisplayName("생성자 참조2")
    @Test
    void otherConstructorReferenceTest() {
        // given
        Function<String, Greeting> hwanGreeting = Greeting::new;
        String name = "hwan";

        // when
        Greeting hwan = hwanGreeting.apply(name);

        // then
        assertThat(hwan.getName()).isEqualTo(name);
    }

    @DisplayName("임의 객체의 인스턴스 메서드 참조: 타입::인스턴스 메서드")
    @Test
    void instanceMethod() {
        String[] names = {"yu", "hwan", "min"};

        // String의 compareToIgnoreCase은 클래스 메서드가 아닌 인스턴스 메서드이다.
        Arrays.sort(names, String::compareToIgnoreCase);

        assertThat(names).containsExactly("hwan", "min", "yu");
    }

    static class Greeting {
        private String name;

        public Greeting() {
        }

        public Greeting(String name) {
            this.name = name;
        }

        public static String hi(String name) {
            return "hi" + name;
        }

        public String getName() {
            return name;
        }

        public String hello(String name) {
            return "hello" + name;
        }
    }
}
