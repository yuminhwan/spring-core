package com.example.java8to11.concurrent;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * CompletableFuture
 *      비동기(Asynchronous) 프로그래밍을 가능케하는 인터페이스
 *
 *  Future를 사용할순 있지만 한계가 있다.
 *      Future를 외부에서 완료 시킬 수 없다. 취소하거나, get()에 타임아웃을 설정할 수는 있다.
 *      블로킹 코드(get())를 사용하지 않고서는 작업이 끝났을 때 콜백을 실행할 수 없다.
 *      여러 Future를 조합할 수 없다, 예) Event 정보 가져온 다음 Event에 참석하는 회원 목록 가져오기
 *      예외 처리용 API를 제공하지 않는다.
 *
 *  원하는 Executor(쓰레드풀)를 사용해서 실행할 수도 있다. (기본은 ForkJoinPool.commonPool())
 *
 *  콜백 자체를 또 다른 쓰레드에서 실행할 수 있다.
 */
class CompletableFutureTest {

    @DisplayName("completedFuture")
    @Test
    void completedFuture() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture.completedFuture("hwan");

        String sut = future.get();

        assertThat(sut).isEqualTo("hwan");
    }

    @DisplayName("runAsync() - 리턴값이 없는 경우")
    @Test
    void runAsync() throws ExecutionException, InterruptedException {
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            System.out.println("Hello" + Thread.currentThread().getName());
        });

        future.get();
    }

    @DisplayName("supplyAsync() - 리턴값이 있는 경우")
    @Test
    void supplyAsync() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("Hello" + Thread.currentThread().getName());
            return "Hello";
        });

        String sut = future.get();

        assertThat(sut).isEqualTo("Hello");
    }

    @DisplayName("thenApply(Function) - 리턴값을 받아서 다른 값으로 바꾸는 콜백")
    @Test
    void thenApply() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("Hello" + Thread.currentThread().getName());
            return "Hello";
        }).thenApply((s) -> {
            System.out.println(Thread.currentThread().getName());
            return s.toUpperCase();
        });

        String sut = future.get();

        assertThat(sut).isEqualTo("HELLO");
    }

    @DisplayName("thenAccept(Consumer) - 리턴값을 또 다른 작업을 처리하는 콜백 (리턴없이)")
    @Test
    void thenAccept() throws ExecutionException, InterruptedException {
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("Hello" + Thread.currentThread().getName());
            return "Hello";
        }).thenAccept((s) -> {
            System.out.println(Thread.currentThread().getName());
            System.out.println(s.toUpperCase());
        });

        future.get();
    }

    @DisplayName("thenRun(Runnable): 리턴값 받지 다른 작업을 처리하는 콜백")
    @Test
    void thenRun() throws ExecutionException, InterruptedException {
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("Hello" + Thread.currentThread().getName());
            return "Hello";
        }).thenRun(() -> {
            System.out.println(Thread.currentThread().getName());
        });

        future.get();
    }

    @DisplayName("원하는 Executor(쓰레드풀)를 사용해서 실행한다.")
    @Test
    void threadPool() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(4);

        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("Hello " + Thread.currentThread().getName());
            return "Hello";
        }, executorService).thenRunAsync(() -> {
            System.out.println(Thread.currentThread().getName());
        }, executorService);

        future.get();

        executorService.shutdown();
    }

    @DisplayName("thenCompose() - 두 작업이 서로 이어서 실행하도록 조합")
    @Test
    void thenCompose() throws ExecutionException, InterruptedException {
        CompletableFuture<String> hello = getCompletableFuture("Hello");
        CompletableFuture<String> world = getWorld(" World");

        // hello 기다린 뒤 world 기다림
        // hello.get();
        // world.get();

        CompletableFuture<String> future = hello.thenCompose(this::getWorld);

        assertThat(future.get()).isEqualTo("Hello World");
    }

    @DisplayName("thenCombine() - 두 작업을 독립적으로 실행하고 둘 다 종료 했을 때 콜백 실행")
    @Test
    void thenCombine() throws ExecutionException, InterruptedException {
        CompletableFuture<String> hello = getCompletableFuture("Hello");
        CompletableFuture<String> world = getCompletableFuture("World");

        CompletableFuture<String> future = hello.thenCombine(world, (h, w) -> h + " " + w);

        assertThat(future.get()).isEqualTo("Hello World");
    }

    /**
     * 블록킹이 없다.
     */
    @DisplayName("allOf() - 여러 작업을 모두 실행하고 모든 작업 결과에 콜백 실행")
    @Test
    void allOf() throws ExecutionException, InterruptedException {
        CompletableFuture<String> hello = getCompletableFuture("Hello");
        CompletableFuture<String> world = getCompletableFuture("World");

        /**
         * 결과값이 같다는 보장도 없다. -> null
         */
        // CompletableFuture<Void> future = CompletableFuture.allOf(hello, world)
        //     .thenAccept(System.out::println);

        List<CompletableFuture<String>> futures = List.of(hello, world);
        CompletableFuture[] futuresArray = futures.toArray(new CompletableFuture[0]);

        /**
         * join은 unchecked
         */
        CompletableFuture<List<String>> results = CompletableFuture.allOf(futuresArray)
            .thenApply(v -> futures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList()));

        results.get().forEach(System.out::println);
    }

    @DisplayName("anyOf() - 여러 작업 중에 가장 빨리 끝난 하나의 결과에 콜백 실행")
    @Test
    void anyOf() throws ExecutionException, InterruptedException {
        CompletableFuture<String> hello = getCompletableFuture("Hello");
        CompletableFuture<String> world = getCompletableFuture("World");

        CompletableFuture<Void> future = CompletableFuture.anyOf(hello, world).thenAccept(System.out::println);

        future.get();
    }

    @DisplayName("exeptionally(Function) - 예외 처리")
    @Test
    void exeptionally() throws ExecutionException, InterruptedException {
        boolean throwError = true;

        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> {
            if (throwError) {
                throw new IllegalArgumentException();
            }

            System.out.println("Hello " + Thread.currentThread().getName());
            return "Hello";
        }).exceptionally(ex -> {
            System.out.println(ex);
            return "Error!";
        });

        assertThat(hello.get()).isEqualTo("Error!");
    }

    @DisplayName("handle(BiFunction) - 정상, 예외 처리")
    @Test
    void handle() throws ExecutionException, InterruptedException {
        boolean throwError = true;

        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> {
            if (throwError) {
                throw new IllegalArgumentException();
            }

            System.out.println("Hello " + Thread.currentThread().getName());
            return "Hello";
        }).handle((result, ex) -> {
            if (ex != null) {
                System.out.println(ex);
                return "Error!";
            }
            return result;
        });

        assertThat(hello.get()).isEqualTo("Error!");
    }

    private CompletableFuture<String> getWorld(String message) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("World " + Thread.currentThread().getName());
            return message + "World";
        });
    }

    private CompletableFuture<String> getCompletableFuture(String message) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println(message + " " + Thread.currentThread().getName());
            return message;
        });
    }
}
