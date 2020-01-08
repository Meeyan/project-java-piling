package com.std.video.geyiming.tec5;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 重入锁demo1：
 * 常规使用模式。
 * 声明锁，加锁，释放锁。不同于synchronized的是，重入锁需要显式释放锁。
 *
 * @author zhaojy
 * @date 2017-07-27
 */
public class D1_ReentrantLock implements Runnable {

    ReentrantLock lock = new ReentrantLock();   // 声明锁
    static int count = 0;

    @Override
    public void run() {
        for (int i = 0; i < 10000000; i++) {
            lock.lock();    // 加锁
            try {
                count++;
            } finally {
                lock.unlock();  // 必须手动释放锁
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        D1_ReentrantLock service = new D1_ReentrantLock();
        Thread th1 = new Thread(service);
        Thread th2 = new Thread(service);

        th1.start();
        th2.start();

        th1.join();
        th2.join();

        System.out.println(count);
    }
}
