package com.example.java8to11.concurrent;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ConcurrentTest {

    /**
     * Thread 상속 방법
     */
    @DisplayName("Thread 상속 - 멀티스레드는 코드 순서대로 동작하지 않는다.")
    @Test
    void extendsThread() {
        MyThread myThread = new MyThread();
        myThread.start();

        System.out.println("Hello: " + Thread.currentThread().getName());
    }

    /**
     * Runnable 넘기는 방법
     */
    @DisplayName("Runnable 넘기는 방식")
    @Test
    void runnableThread() {
        Thread thread = new Thread(() -> System.out.println("Thread:" + Thread.currentThread().getName()));
        thread.start();

        System.out.println("Hello: " + Thread.currentThread().getName());
    }

    @DisplayName("sleep - 스레드 동작 제어")
    @Test
    void sleep() {
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread:" + Thread.currentThread().getName());
        });
        thread.start();

        System.out.println("Hello: " + Thread.currentThread().getName());
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @DisplayName("인터럽트 - 일시정지 상태의 스레드를 깨워 InterruptedException 발생")
    @Test
    void interrupt() throws InterruptedException {
        Thread thread = new Thread(() -> {
            while (true) {
                System.out.println("Thread:" + Thread.currentThread().getName());
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    System.out.println("interrupted!");
                    return; // return 하지 않으면 계속 돌아간다. -> return이 종료시키는 과정
                }
            }
        });
        thread.start();

        System.out.println("Hello: " + Thread.currentThread().getName());
        Thread.sleep(3000L);
        thread.interrupt();
    }

    /**
     * Thread 관리가 매우 복잡하다.
     * -> Executors
     */
    @DisplayName("join - 해당 스레드가 끝날 때까지 기다린다")
    @Test
    void join() {
        Thread thread = new Thread(() -> {
            System.out.println("Thread:" + Thread.currentThread().getName());
            try {
                Thread.sleep(3000L);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
        });
        thread.start();

        System.out.println("Hello: " + Thread.currentThread().getName());
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace(); // main thread에게 인터럽트 요청이 오면 InterruptedException 발생
        }
        System.out.println(thread + "is finished");
    }

    static class MyThread extends Thread {

        @Override
        public void run() {
            System.out.println("Thread:" + Thread.currentThread().getName());
        }
    }
}
