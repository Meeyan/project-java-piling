package com.std.video.geyiming.tec5;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Semaphore示例
 *
 * @author zhaojy
 * @createTime 2017-12-25
 */
public class D6_Semaphore implements Runnable {
    final Semaphore semaphore = new Semaphore(5);   // 指定5个许可

    @Override
    public void run() {
        try {
            semaphore.acquire();    // 也可以获取多个许可。
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getId() + " ---- done..");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            semaphore.release();    // 释放许可
        }
    }

    public static void main(String[] args) {
        ExecutorService exec = Executors.newFixedThreadPool(20);
        final D6_Semaphore semaphoreDemo = new D6_Semaphore();
        for (int i = 0; i < 20; i++) {
            exec.submit(semaphoreDemo);
        }
    }
}
