package com.example.springdi.bytecode;

import static net.bytebuddy.matcher.ElementMatchers.*;

import java.io.File;
import java.io.IOException;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.ClassFileLocator;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.pool.TypePool;

/**
 * 문자열로 클래스를 참조함으로써 로딩이 안되도록 함.
 */
public class BuddyMasulsa {

    public static void main(String[] args) {
        ClassLoader classLoader = BuddyMasulsa.class.getClassLoader();
        TypePool typePool = TypePool.Default.of(classLoader);
        try {
            new ByteBuddy().redefine(typePool.describe("com.example.springdi.bytecode.Moja").resolve(),
                    ClassFileLocator.ForClassLoader.of(classLoader))
                .method(named("pullOut"))
                .intercept(FixedValue.value("Rabbit!"))
                .make()
                .saveIn(new File(
                    "/Users/yuminhwan/Desktop/study/programmers/my-lab/spring-lab/spring-core/spring-di/out/production/classes/"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(new Moja().pullOut());
    }
}
