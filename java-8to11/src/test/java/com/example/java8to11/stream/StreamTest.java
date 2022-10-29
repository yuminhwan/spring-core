package com.example.java8to11.stream;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 *  Stream
 *  데이터를 담고 있는 저장소 (컬렉션)이 아니다. -> 데이터 소스를 추상화, 자주 사용되는 메서드 정의 -> 데이터의 흐름?
 *  Funtional in nature, 스트림이 처리하는 데이터 소스를 변경하지 않는다. -> 읽기만 한다.
 *  스트림으로 처리하는 데이터는 오직 한번만 처리한다.
 *  내부 반복이다.
 *  중간 연산 : Stream 반환
 *  최종 연산 : Stream 반환 X
 *  중간 연산은 근복적으로 lazy -> 최종 연산이 수행되기 전까지는 중간 연산이 수행되지 않는다.
 *
 *  for문은 병렬처리가 어렵지만 Stream은 병렬적으로 가능(parallelStream)
 */
class StreamTest {

    private List<String> names;

    @BeforeEach
    void setUp() {
        names = List.of("hwan", "tee", "yu");
    }

    @DisplayName("스트림에 연산을 해도 원본은 변경되지 않는다.")
    @Test
    void notModifiedOriginal() {
        // given
        // when
        Stream<String> stringStream = names.stream()
            .map(String::toUpperCase);

        // then
        assertThat(names.get(0)).isUpperCase();
    }

    @DisplayName("최종 연산이 수행되기 전까지는 중간 연산이 수행되지 않는다.")
    @Test
    void lazyCalculate() {
        Stream<String> stringStream = names.stream()
            .map(s -> {
                System.out.println(s);
                return s.toUpperCase();
            });

        System.out.println("==============");

        names.forEach(System.out::println);
    }

    /**
     * 병렬 스트림이 무조건 좋은 건 아니다.
     * -> Thread 생성, Context Switching 비용
     */
    @DisplayName("병렬 스트림")
    @Test
    void parallelStream() {
        List<String> collect = names.parallelStream().map((s) -> {
            System.out.println(s + " " + Thread.currentThread().getName());
            return s.toUpperCase();
        }).collect(Collectors.toList());

        collect.forEach(System.out::println);
    }
}
