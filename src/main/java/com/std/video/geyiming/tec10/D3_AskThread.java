package com.std.video.geyiming.tec10;

import java.util.concurrent.CompletableFuture;

/**
 * 演示使用CompletableFuture
 * @author zhaojy
 * @date 2018-01-18
 */
public class D3_AskThread implements Runnable {
    CompletableFuture<Integer> re = null;

    public D3_AskThread(CompletableFuture<Integer> re) {
        this.re = re;
    }

    @Override
    public void run() {
        int myRe = 0;
        try {
            myRe = re.get() * re.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(myRe);
    }

    public static void main(String[] args) throws InterruptedException {
        final CompletableFuture<Integer> future = new CompletableFuture<>();
        new Thread(new D3_AskThread(future)).start();

        Thread.sleep(1000);

        future.complete(60);

    }
}
