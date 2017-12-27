package com.std.video.geyiming.tec6;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 演示使用ThreadPool
 *
 * @author zhaojy
 * @create-time 2017-12-27
 */
public class D1_FixThreadPoolDemo {
    static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println("----Thread Id:" + Thread.currentThread().getId());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            Thread mThread = new MyThread();
            Future<?> submit = executorService.submit(mThread);
        }
    }
}
