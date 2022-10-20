package com.example.springdi.book;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})  //어디에 붙일 지 제한할 수 있다.
@Inherited  // 상위 클래스 어노테이션 조회 가능
public @interface MyRuntimeAnnotation {

    String value() default "default"; // 명시안해도 된다!

    String name() default "hwan";

    int number() default 100;
}
