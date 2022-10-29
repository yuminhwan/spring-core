package com.example.java8to11.concurrent;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Callable
 *   Runnable과 유사하지만 작업의 결과를 받을 수 있다.
 *
 * Future
 *    비동기적인 작업의 현재 상태를 조회하거나 결과를 가져올 수 있다.
 */
class CallableTest {

    private ExecutorService executorService;

    @BeforeEach
    void setUp() {
        executorService = Executors.newFixedThreadPool(4);
    }

    @AfterEach
    void tearDown() {
        executorService.shutdown();
    }

    @DisplayName("Future의 get()은 블로킹 콜이다.")
    @Test
    void futureGet() throws ExecutionException, InterruptedException {
        Callable<String> hello = () -> {
            Thread.sleep(2000L);
            return "Hello";
        };

        Future<String> helloFuture = executorService.submit(hello);
        System.out.println("Started!");

        // 블록킹 콜  - 값을 가져올 땐 기다린다.
        // 타임아웃(최대한으로 기다릴 시간)을 설정할 수 있다.
        helloFuture.get();

        System.out.println("End!");
    }

    @DisplayName("isDone - 끝났는 지 확인")
    @Test
    void isDone() {
        Callable<String> hello = () -> {
            Thread.sleep(2000L);
            return "Hello";
        };

        Future<String> helloFuture = executorService.submit(hello);
        System.out.println(helloFuture.isDone());
        System.out.println("Started!");

        /**
         * parameter로 true를 전달하면 현재 진행중인 쓰레드를 interrupt하고
         * 그러지 않으면 현재 진행중인 작업이 끝날때까지 기다린다.
         * false일 때도 get으로 값을 가져올 수 없다.
         */
        helloFuture.cancel(true);
        System.out.println(helloFuture.isDone());
        System.out.println("End!");
    }

    @DisplayName("invokeAll - 동시에 실행한 작업 중에 제일 오래 걸리는 작업 만큼 시간이 걸린다.\n")
    @Test
    void invokeAll() throws ExecutionException, InterruptedException {
        Callable<String> hello = () -> {
            Thread.sleep(2000L);
            return "Hello";
        };

        Callable<String> java = () -> {
            Thread.sleep(3000L);
            return "Java";
        };

        Callable<String> hwan = () -> {
            Thread.sleep(1000L);
            return "Hwan";
        };

        List<Future<String>> futures = executorService.invokeAll(List.of(hello, java, hwan));
        for (Future<String> f : futures) {
            System.out.println(f.get());
        }
    }

    @DisplayName("invokeAny - 동시에 실행한 작업 중에 제일 짧게 걸리는 작업 만큼 시간이 걸린다.")
    @Test
    void invokeAny() throws ExecutionException, InterruptedException {
        Callable<String> hello = () -> {
            Thread.sleep(2000L);
            return "Hello";
        };

        Callable<String> java = () -> {
            Thread.sleep(3000L);
            return "Java";
        };

        Callable<String> hwan = () -> {
            Thread.sleep(1000L);
            return "Hwan";
        };

        // 블록킹 콜
        String sut = executorService.invokeAny(List.of(hello, java, hwan));

        assertThat(sut).isEqualTo("Hwan");
    }
}
