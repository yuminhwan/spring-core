package com.example.springdi.bytecode;

import static net.bytebuddy.matcher.ElementMatchers.*;

import java.io.File;
import java.io.IOException;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.FixedValue;

/**
 * 주석의 이유
 * main 실행 시 -> Moja 클래스 파일이 로딩되고 메모리 상에 올라가게 된다.
 * 그 이후 바이트코드를 조작하더라도 이미 로딩이 되어있기 때문에 로딩된 메모리를 바라보게 된다.
 * 고로, 같이 사용하면 안되고 미리 바이트코드를 조작하고 실행해야한다.
 */
public class Masulsa {

    public static void main(String[] args) {
        // fixByteCode();

        System.out.println(new Moja().pullOut());
    }

    private static void fixByteCode() {
        try {
            new ByteBuddy().redefine(Moja.class)
                .method(named("pullOut"))
                .intercept(FixedValue.value("Rabbit!"))
                .make().saveIn(new File("/Users/yuminhwan/Desktop/study/programmers/my-lab/spring-lab/spring-core/spring-di/out/production/classes/"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
