package com.example.java8to11.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Executors가 하는 일
 *    쓰레드 만들기: 애플리케이션이 사용할 쓰레드 풀을 만들어 관리한다.
 *    쓰레드 관리: 쓰레드 생명 주기를 관리한다.
 *    작업 처리 및 실행: 쓰레드로 실행할 작업을 제공할 수 있는 API를 제공한다.
 */
class ExecutorsTest {

    static Runnable getRunnable(String message) {
        return () -> System.out.println(message + Thread.currentThread().getName());
    }

    @DisplayName("newSingleThreadExecutor - 스레드 하나만 가질 수 있는 스레드풀 생성 ")
    @Test
    void newSingleThreadExecutor() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        executorService.submit(getRunnable("Thread "));

        // ExecutorService를 종료시키지 않으면 계속 다음 Task 수행을 위해 스레드가 계속 대기한다.
        // 스레드의 일을 끝까지 한 뒤 종료
        executorService.shutdown();

        // 바로 종료
        // executorService.shutdownNow();
    }

    @DisplayName("newFixedThreadPool(int) - 인자로 주어진 숫자만큼의 스레드를 가진 풀 생성")
    @Test
    void newFixedThreadPool() {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(getRunnable("Hello "));
        executorService.submit(getRunnable("Hwan "));
        executorService.submit(getRunnable("The "));
        executorService.submit(getRunnable("Java "));
        executorService.submit(getRunnable("Thread "));

        executorService.shutdown();
    }

    @DisplayName("ScheduledExecutorService - 스케줄")
    @Test
    void scheduledExecutorService() throws InterruptedException {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        // executorService.schedule(getRunnable("Hello "), 3, TimeUnit.SECONDS);
        executorService.scheduleAtFixedRate(getRunnable("Hello "), 1, 2, TimeUnit.SECONDS);

        Thread.sleep(5000L);
    }
}
