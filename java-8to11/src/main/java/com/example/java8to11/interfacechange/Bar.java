package com.example.java8to11.interfacechange;

public interface Bar extends Foo {

    // 인터페이스를 상속받는 인터페이스에서 다시 추상 메소드로 변경할 수 있다.
    String returnNameUpperCase();
}
