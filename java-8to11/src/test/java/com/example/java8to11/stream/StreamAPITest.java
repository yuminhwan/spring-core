package com.example.java8to11.stream;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StreamAPITest {

    private List<OnlineClass> springClasses;
    private List<OnlineClass> javaClasses;
    private List<List<OnlineClass>> keesunEvents;

    @BeforeEach
    void setUp() {
        springClasses = List.of(new OnlineClass(1, "spring boot", true),
            new OnlineClass(2, "spring data jpa", true),
            new OnlineClass(3, "spring mvc", false),
            new OnlineClass(4, "spring core", false),
            new OnlineClass(5, "rest api development", false)
        );

        javaClasses = List.of(new OnlineClass(6, "The Java, Test", true),
            new OnlineClass(7, "The Java, Code manipulation", true),
            new OnlineClass(8, "The Java, 8 to 11", false));

        keesunEvents = List.of(springClasses, javaClasses);
    }

    @DisplayName("spring으로 시작하는 수업")
    @Test
    void startWithSpring() {
        List<OnlineClass> sut = springClasses.stream()
            .filter(onlineClass -> onlineClass.getTitle().startsWith("spring"))
            .collect(Collectors.toList());

        assertThat(sut).hasSize(4);
    }

    @DisplayName("close 되지 않은 수업")
    @Test
    void notClosed() {
        // given
        List<OnlineClass> sut = springClasses.stream()
            .filter(Predicate.not(OnlineClass::isClosed))
            .collect(Collectors.toList());

        assertThat(sut).hasSize(3);
    }

    @DisplayName("수업 이름만 모아서 스트림 만들기")
    @Test
    void onlyClassNameStream() {
        List<String> sut = springClasses.stream()
            .map(OnlineClass::getTitle)
            .collect(Collectors.toList());

        assertThat(sut).containsExactly("spring boot",
            "spring data jpa",
            "spring mvc",
            "spring core",
            "rest api development");
    }

    @DisplayName("두 수업 목록에 들어있는 모든 수업 아이디 출력")
    @Test
    void getAllClassId() {
        List<Integer> sut = keesunEvents.stream().flatMap(Collection::stream)
            .map(OnlineClass::getId)
            .collect(Collectors.toList());

        assertAll(
            () -> assertThat(sut).hasSize(8),
            () -> assertThat(sut).containsExactly(1, 2, 3, 4, 5, 6, 7, 8)
        );
    }

    @DisplayName("10부터 1씩 증가하는 무제한 스트림 중에서 앞에 10개 빼고 최대 10개 까지만")
    @Test
    void unlimitedStream() {
        List<Integer> sut = Stream.iterate(10, i -> i + 1)
            .skip(10)
            .limit(10)
            .collect(Collectors.toList());

        assertAll(
            () -> assertThat(sut).hasSize(10),
            () -> assertThat(sut).containsExactly(20, 21, 22, 23, 24, 25, 26, 27, 28, 29)
        );
    }

    @DisplayName("자바 수업 중에 Test가 들어있는 수업이 있는 지 확인")
    @Test
    void isJavaClassNameContainsTest() {
        boolean sut = javaClasses.stream()
            .anyMatch(onlineClass -> onlineClass.getTitle().contains("Test"));

        assertThat(sut).isTrue();
    }

    @DisplayName("스프링 수업 중에 제목에 spring이 들어간 것만 모아서 List로 만들기")
    @Test
    void getSpringClassNameContainsSpring() {
        List<String> sut = springClasses.stream()
            .map(OnlineClass::getTitle)
            .filter(title -> title.contains("spring"))
            .collect(Collectors.toList());

        assertThat(sut).hasSize(4);
    }
}
