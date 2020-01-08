package com.std.video.geyiming.tec5;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * CountDownLatch演示
 *
 * @author zhaojy
 * @date 2017-12-25
 */
public class D8_CountDownLatch implements Runnable {
    static final CountDownLatch countDownLatch = new CountDownLatch(10); // 10个

    @Override
    public void run() {
        try {
            Thread.sleep(new Random().nextInt(10) * 1000);
            System.out.println(Thread.currentThread().getId() + " ---- check complete..");
            countDownLatch.countDown(); // 通知完成
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        D8_CountDownLatch cd = new D8_CountDownLatch();
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            executorService.submit(cd);
        }

        countDownLatch.await();
        System.out.println("Fire!");
        executorService.shutdown();
    }
}
