package com.std.video.geyiming.tec4;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 非阻塞式同步--Integer类型
 * 无锁式，实现同步
 *
 * @author zhaojy
 * @createTime 2017-05-27
 */
public class D1_AtomicIntegerDemo {
    private static AtomicInteger atomicInteger = new AtomicInteger();

    public static class AddThread implements Runnable {
        @Override
        public void run() {
            for (int k = 0; k < 10000; k++) {
                atomicInteger.incrementAndGet();    // CAS操作
            }
        }
    }


    static int count = 0;

    /**
     * 使用加锁方式处理
     */
    static class AddThreadS implements Runnable {
        static final Object lock = new Object();

        @Override
        public void run() {
            // 两种写法，for循环和lock的位置互调，频繁的竞争锁会增加开销
            synchronized (lock) {
                for (int k = 0; k < 10000; k++) {
                    count++;
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        int threadCnt = 150;    // 线程的数量
        Thread[] ts = new Thread[threadCnt];
        for (int i = 0; i < threadCnt; i++) {
            ts[i] = new Thread(new AddThread());
        }

        for (int i = 0; i < threadCnt; i++) {
            ts[i].start();
        }

        for (int i = 0; i < threadCnt; i++) {
            ts[i].join();
        }
        System.out.println("耗时:" + (System.currentTimeMillis() - startTime));
        System.out.println(atomicInteger);

        // 前后两次对比

        long startTime_2 = System.currentTimeMillis();
        Thread[] tas = new Thread[threadCnt];
        for (int i = 0; i < threadCnt; i++) {
            tas[i] = new Thread(new AddThreadS());
        }

        for (int i = 0; i < threadCnt; i++) {
            tas[i].start();
        }

        for (int i = 0; i < threadCnt; i++) {
            tas[i].join();
        }

        System.out.println("耗时:" + (System.currentTimeMillis() - startTime_2));
        System.out.println(count);

    }
}
