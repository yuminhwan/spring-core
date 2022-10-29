package com.example.java8to11.optional;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OptionalAPITest {

    private List<OnlineClass> springClasses;

    @BeforeEach
    void setUp() {
        springClasses = List.of(new OnlineClass(1, "spring boot", true),
            new OnlineClass(5, "rest api development", false)
        );
    }

    @DisplayName("isPresent - 값이 있는 지 없는 지 확인한다.")
    @Test
    void isPresent() {
        Optional<OnlineClass> spring = springClasses.stream()
            .filter(oc -> oc.getTitle().startsWith("spring"))
            .findFirst();

        boolean sut = spring.isPresent();

        assertThat(sut).isTrue();
    }

    @DisplayName("isEmpty - 자바11부터 제공한다.")
    @Test
    void isEmpty() {
        Optional<OnlineClass> jpa = springClasses.stream()
            .filter(oc -> oc.getTitle().startsWith("jpa"))
            .findFirst();

        boolean sut = jpa.isEmpty();

        assertThat(sut).isTrue();
    }

    /**
     * Optional안에 null이라면 get사용 시 NoSuchElementException 발생
     */
    @DisplayName("get - Optional에서 값을 꺼낸다.")
    @Test
    void get() {
        Optional<OnlineClass> spring = springClasses.stream()
            .filter(oc -> oc.getTitle().startsWith("spring"))
            .findFirst();

        OnlineClass sut = spring.get();

        assertThat(sut.getTitle()).isEqualTo("spring boot");
    }

    @DisplayName("ifPresent(Consumer) - 값이 있을 경우 그 값으로 ~~를 해라")
    @Test
    void ifPresent() {
        Optional<OnlineClass> jpa = springClasses.stream()
            .filter(oc -> oc.getTitle().startsWith("jpa"))
            .findFirst();

        jpa.ifPresent(oc -> System.out.println(oc.getId()));
    }

    /**
     * 값이 있더라도 연산을 무조건 하게 된다. -> 문제가 발생할 수 있다.
     */
    @DisplayName("orElse(T) - 값이 있으면 가져오고 없는 경우 ~~를 리턴하라.")
    @Test
    void orElse() {
        Optional<OnlineClass> jpa = springClasses.stream()
            .filter(oc -> oc.getTitle().startsWith("jpa"))
            .findFirst();

        OnlineClass onlineClass = jpa.orElse(createNewClass());

        assertThat(onlineClass.getTitle()).isEqualTo("New class");
    }

    /**
     * orElse와 달리 있을 땐 연산이 실행되지 않는다.
     * 다만, 상수나 이미 만들어져 있다면 orElse를 써도 된다.
     */
    @DisplayName("orElseGet(Supplier) - 있으면 가져오고 없는 경우에 ~~를 하라")
    @Test
    void orElseGet() {
        Optional<OnlineClass> jpa = springClasses.stream()
            .filter(oc -> oc.getTitle().startsWith("jpa"))
            .findFirst();

        OnlineClass onlineClass = jpa.orElseGet(this::createNewClass);

        assertThat(onlineClass.getTitle()).isEqualTo("New class");
    }

    @DisplayName("orElseThrow - 값이 있으면 가져오 없는 경우 에러를 던져라.")
    @Test
    void orElseThrow() {
        Optional<OnlineClass> jpa = springClasses.stream()
            .filter(oc -> oc.getTitle().startsWith("jpa"))
            .findFirst();

        assertThatThrownBy(() -> jpa.orElseThrow(IllegalArgumentException::new))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("Optional filter(Predicate) - 들어있는 값 걸러내기")
    @Test
    void filter() {
        Optional<OnlineClass> onlineClass = springClasses.stream()
            .filter(oc -> oc.getTitle().startsWith("spring"))
            .findFirst();

        Optional<OnlineClass> sut = onlineClass.filter(oc -> !oc.isClosed());

        assertThat(sut).isEmpty();
    }

    @DisplayName("Optional map(Function) - 들어있는 값 변환하기")
    @Test
    void map() {
        Optional<OnlineClass> onlineClass = springClasses.stream()
            .filter(oc -> oc.getTitle().startsWith("spring"))
            .findFirst();

        Optional<Integer> sut = onlineClass.map(OnlineClass::getId);

        assertThat(sut).isNotEmpty();
    }

    @DisplayName("Optional flatMap(Function) - Optional 안에 들어있는 인스턴스가 Optional인 경우에 사용하면 편리하다. ")
    @Test
    void flatMap() {
        Optional<OnlineClass> onlineClass = springClasses.stream()
            .filter(oc -> oc.getTitle().startsWith("spring"))
            .findFirst();

        Optional<Progress> progress = onlineClass.flatMap(OnlineClass::getProgress);
        /*
        -> Optional<Optional<Progress>> progress1 = onlineClass.map(OnlineClass::getProgress);
           Optional<Progress> progress2 = progress1.orElse(Optional.empty())
         */

        assertThat(progress).isEmpty();
    }

    private OnlineClass createNewClass() {
        System.out.println("creating new online class");
        return new OnlineClass(10, "New class", false);
    }
}
