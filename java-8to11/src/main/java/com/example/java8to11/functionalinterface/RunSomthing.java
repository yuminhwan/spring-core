package com.example.java8to11.functionalinterface;

/**
 * 함수형 인터페이스
 * 추상 메서드가 `하나`인 인터페이스
 *
 * java 8부터 static, default 메서드 추가 -> 추상 메서드가 아니므로 영향 X
 *
 * @FunctionalInterface을 달아주는 것이 좋다. -> 컴파일에서 확인
 */
@FunctionalInterface
public interface RunSomthing {

    static void printName() {
        System.out.println("hwan");
    }

    void doIt();

    default void printAge() {
        System.out.println(26);
    }
}
