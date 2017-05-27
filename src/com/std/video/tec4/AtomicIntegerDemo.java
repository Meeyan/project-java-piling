package com.std.video.tec4;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 非阻塞式同步--Integer类型
 * 无锁式，实现同步
 *
 * @author zhaojy
 * @createTime 2017-05-27
 */
public class AtomicIntegerDemo {
    private static AtomicInteger atomicInteger = new AtomicInteger();

    public static class AddThread implements Runnable {
        @Override
        public void run() {
            for (int k = 0; k < 10000; k++) {
                atomicInteger.incrementAndGet();    // CAS操作
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread[] ts = new Thread[10];
        for (int i = 0; i < 10; i++) {
            ts[i] = new Thread(new AddThread());
        }

        for (int i = 0; i < 10; i++) {
            ts[i].start();
        }

        for (int i = 0; i < 10; i++) {
            ts[i].join();
        }

        System.out.println(atomicInteger);
    }
}
