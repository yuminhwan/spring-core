package com.example.java8to11;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class NumberSomethingTest {

    @DisplayName("자바 8이전 익명 내부 클래스 방식")
    @Test
    void functionalInterfaceBeforeJava8() {
        RunSomthing runSomthingBefore8 = new RunSomthing() {
            @Override
            public void doIt() {
                System.out.println("hello");
            }
        };
    }

    @DisplayName("자바 8이후 람다 방식")
    @Test
    void functionalInterfaceAfterJava8() {
        // 익명 클래스 방식과 내부적으로 동일하다. -> 특수한 형태의 오브젝트 -> 일급 객체
        RunSomthing runSomthingAfter8 = () -> System.out.println("Hello");

        // 한줄일때만 줄여서 사용 가능
        RunSomthing runSomthing = () -> {
            System.out.println("Hello");
            System.out.println("Lambda");
        };
    }

    @DisplayName("함수형 인터페이스는 순수 함수이다. - 항상 같은 값을 반환")
    @Test
    void pureFunctionAlwaysSameResult() {
        NumberSomething numberSomething = (number) -> number + 10;

        assertAll(
            () -> assertThat(numberSomething.doIt(10)).isEqualTo(20),
            () -> assertThat(numberSomething.doIt(10)).isEqualTo(20),
            () -> assertThat(numberSomething.doIt(10)).isEqualTo(20)
        );
    }

    /**
     *   Effectively final -> final은 아니지만, 값이 변하지 않는 함수 (자바8)
     *   람다 캡처링
     *
     *   순수 함수는 오직 전달받은 값만 사용해야 한다. 외부에 의존 X
     */
    @DisplayName("순수 함수는 외부값을 변경할 수 없다. 또한, final이나  effectively final이 아닐 경우 참조할 수 없다.")
    @Test
    void pureFunctionExternalVariable() {
        int baseNumber = 10;

        // baseNumber++; -> 람다에서 참조 불가능

        NumberSomething numberSomething = new NumberSomething() {
            int innerNumber = 10;

            @Override
            public int doIt(int num) {
                // baseNumber++; 불가능!
                innerNumber++; // 가능은 하지만 순수 함수라고 보긴 어렵다.
                return num + baseNumber;
            }
        };

        assertThat(numberSomething.doIt(10)).isEqualTo(20);
    }
}
