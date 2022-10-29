package com.example.java8to11;

import static org.assertj.core.api.Assertions.*;

import java.util.function.BinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.Supplier;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 람다
 * (인자 리스트) -> {바디}
 *
 * 익명 내부 클래스와 차이점
 * 1. 익명 내부 클래스는 새로운 클래스를 생성하지만, 람다는 새로운 메서드를 생성하여 포함한다.
 * 2. 익명 내부 클래스 this는 새로 생성된 클래스를 가르키지만, 람다는 람다가 있는 클래스를 가르킨다.
 */
class LambdaTest {

    @DisplayName("람다의 인자가 없을 때 ()로 표현한다. 또한, 바디가 한 줄인 경우 괄호를 생략할 수 있고 return도 생략 가능하다.")
    @Test
    void noArgsAndBodyOneLine() {
        // given
        Supplier<Integer> getTen = () -> 10;

        // when
        int sut = getTen.get();

        // then
        assertThat(sut).isEqualTo(10);
    }

    /**
     * 인자 타입 생략 가능 -> 컴파일러가 추론해주기 때문에
     */
    @DisplayName("인자가 한개일 경우 (one) 또는 one으로 표현하고 여러개일 때 (one,two)로 표현한다.")
    @Test
    void args() {
        // given
        BinaryOperator<Integer> add = (a, b) -> a + b;

        // when
        Integer sut = add.apply(10, 20);

        // then
        assertThat(sut).isEqualTo(30);
    }

    /**
     * 익명 클래스에서도 쓰이던 기능
     * 자바 8 이전에는 final이여야지 참조 가능
     * 자바 8 이후에는 final 또는 Effectively final일 경우 가능
     *
     * 쉐도윙 -> 만약 로컬 클래스에 number라는 변수가 있고 메서드의 지역변수에도 number라고 있을 시
     *           로컬 클래스의 number로 메서드의 지역변수 number가 가려지는 현상
     *           별도의 scope이기 때문에 (람다의 scope는 람다를 감싸는 메서드, 클래스와 같다.
     */
    @DisplayName("변수 캡쳐")
    @Test
    void variableCapture() {
        // Effectively final 세 곳 모두에서 참조 가능
        int baseNumber = 10;

        // 로컬 클래스 - 메서드 내부에서 정의된 클래스
        // 쉐도윙
        class LocalClass {
            void printBaseNumber() {
                int baseNumber = 11;
                System.out.println(baseNumber); // 11
            }
        }

        // 익명 클래스
        // 쉐도윙
        IntConsumer printInt = new IntConsumer() {
            @Override
            public void accept(int baseNumber) {
                System.out.println(baseNumber);  // 파라미터의 baseNumber
            }
        };

        // 람다
        // 쉐도윙 X
        IntConsumer printIntLambda = number -> {
            // int baseNumber = 10;  불가능, 인자에도 불가능

            System.out.println(number + baseNumber);
        };

        printIntLambda.accept(10);
    }
}
