package com.example.java8to11.interfacechange;

/**
 * 동일한 메서드 이름이기 때문에 어떤 것을 써야할 지 모른다.
 * -> 오버라이딩 필요
 */
public class DefaultFoo implements Foo, Bam {

    String name;

    public DefaultFoo(String name) {
        this.name = name;
    }

    @Override
    public String returnName() {
        return this.name;
    }

    @Override
    public String returnNameUpperCase() {
        return Foo.super.returnNameUpperCase();
    }
}
