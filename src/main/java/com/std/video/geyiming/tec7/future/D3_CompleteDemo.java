package com.std.video.geyiming.tec7.future;


import java.util.concurrent.CompletableFuture;

/**
 *
 * @author zhaojy
 * @create-time 2018-01-24
 */
public class D3_CompleteDemo {
    public static void main(String[] args) {
        // CompletableFuture.supplyAsync(() -> "hello").thenAccept(s -> System.out.println(s + " world"));
        testThenRun();
    }

    static void testThenRun() {
        CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "hello";
        }).thenRun(() -> System.out.println(" hello world"));

        // 没有这个，无法打印。
        while (true) {

        }
    }
}
