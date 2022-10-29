package com.example.java8to11.interfacechange;

/**
 * default 메서드
 * 인터페이스에 메소드 선언이 아니라 구현체를 제공하는 방법
 * 해당 인터페이스를 구현한 클래스를 깨트리지 않고 새 기능을 추가할 수 있다.
 * 주의! 기본 메소드는 구현체가 모르게 추가된 기능으로 그만큼 리스크가 있다. (모든 구현 클래스에 영향)
 *
 * 인터페이스 구현체가 재정의 할 수도 있다.
 * Object가 제공하는 기능 (equals, hasCode)는 기본 메소드로 제공할 수 없다.
 * -> 구현체에서 재정의해야 함
 */
public interface Foo {

    static String returnAnyting() {
        return "any";
    }

    String returnName();

    default String returnNameUpperCase() {
        return returnName().toUpperCase();
    }
}
