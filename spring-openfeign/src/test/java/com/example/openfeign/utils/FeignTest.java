package com.example.openfeign.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(
    classes = {FeignTestContext.class},
    properties = {
        "spring.profiles.active=local"
    })
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface FeignTest {
}
