package com.example.java8to11.functionalinterface;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DefaultFunctionTest {

    @DisplayName("Function<T, R>: T 타입을 받아서 R 타입을 리턴하는 함수 인터페이스")
    @Test
    void function() {
        // given
        PlusTen plusTen = new PlusTen();
        int number = 10;

        // when
        Integer sut = plusTen.apply(number);

        // then
        assertThat(sut).isEqualTo(20);
    }

    @DisplayName("Function 람다식 버전")
    @Test
    void functionLambda() {
        // given
        Function<Integer, Integer> plusTen = (num) -> num + 10;
        int number = 10;

        // when
        Integer sut = plusTen.apply(number);

        // then
        assertThat(sut).isEqualTo(20);
    }

    @DisplayName("Function의 compose는 입력값을 먼저 적용 후 해당 로직의 입력값으로 사용한다.")
    @Test
    void functionCompose() {
        // given
        Function<Integer, Integer> plusTen = (num) -> num + 10;
        Function<Integer, Integer> multiplyTwo = (num) -> num * 2;

        // when
        Function<Integer, Integer> multiplyTwoAndPlusTen = plusTen.compose(multiplyTwo);
        Integer sut = multiplyTwoAndPlusTen.apply(2);

        // then
        assertThat(sut).isEqualTo(14);
    }

    @DisplayName("Function의 andThen은 compose와 반대로 뒤에 적용한다.")
    @Test
    void andThen() {
        // given
        Function<Integer, Integer> plusTen = (num) -> num + 10;
        Function<Integer, Integer> multiplyTwo = (num) -> num * 2;

        // when
        Function<Integer, Integer> plusTenAndMultiplyTwo = plusTen.andThen(multiplyTwo);
        Integer sut = plusTenAndMultiplyTwo.apply(2);

        // then
        assertThat(sut).isEqualTo(24);
    }

    @DisplayName("BiFunction<T, U, R>: 두 개의 값(T, U)를 받아서 R 타입을 리턴하는 함수 인터페이스 ")
    @Test
    void biFunction() {
        // given
        BiFunction<Integer, Integer, Integer> plus = (numberOne, numberTwo) -> numberOne + numberTwo;

        // when
        Integer sut = plus.apply(10, 20);

        // then
        assertThat(sut).isEqualTo(30);
    }

    @DisplayName("Consumer<T>: T 타입을 받아서 아무값도 리턴하지 않는 함수 인터페이스")
    @Test
    void consumer() {
        // given
        Consumer<Integer> printT = (number) -> System.out.println(number);

        // when
        // then
        printT.accept(2);
    }

    @DisplayName("Supplier<T>: T 타입의 값을 제공하는 함수 인터페이스")
    @Test
    void supplier() {
        // given
        Supplier<Integer> getTen = () -> 10;

        // when
        int sut = getTen.get();

        // then
        assertThat(sut).isEqualTo(10);
    }

    @DisplayName("Predicate<T>: T 타입을 받아서 boolean을 리턴하는 함수 인터페이스")
    @Test
    void predicate() {
        // given
        Predicate<Integer> isEven = (number) -> number % 2 == 0;

        // when
        boolean sut = isEven.test(10);
        boolean negate = isEven.negate().test(10);
        boolean and = isEven.and((number) -> number == 13).test(10);
        boolean or = isEven.or((number) -> number == 13).test(13);

        // then
        assertAll(
            () -> assertThat(sut).isTrue(),
            () -> assertThat(negate).isFalse(),
            () -> assertThat(and).isFalse(),
            () -> assertThat(or).isTrue()
        );
    }

    @DisplayName("UnaryOperator<T>: Function<T, R>의 특수한 형태로, 입력값 하나를 받아서 동일한 타입을 리턴하는 함수 인터페이스")
    @Test
    void unaryOperator() {
        // given
        UnaryOperator<Integer> plusTen = (num) -> num + 10;

        // when
        Integer sut = plusTen.apply(10);

        // then
        assertThat(sut).isEqualTo(20);
    }

    @DisplayName("BinaryOperator<T>: BiFunction<T, U, R>의 특수한 형태로, 동일한 타입의 입렵값 두개를 받아 리턴하는 함수 인터페이스")
    @Test
    void binaryOperator() {
        // given
        BinaryOperator<Integer> plus = (numberOne, numberTwo) -> numberOne + numberTwo;

        // when
        Integer sut = plus.apply(10, 20);

        // then
        assertThat(sut).isEqualTo(30);
    }

    static class PlusTen implements Function<Integer, Integer> {

        @Override
        public Integer apply(Integer number) {
            return number + 10;
        }
    }
}
