package com.std.video.tec5;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 重入锁demo2：
 * 重入特性：可以多次加锁，但是也必须多次释放锁（加锁和释放锁的次数必须保持一致）
 *
 * @author zhaojy
 * @createTime 2017-07-27
 */
public class D2_ReentrantLock implements Runnable {

    ReentrantLock lock = new ReentrantLock();   // 声明锁
    static int count = 0;

    @Override
    public void run() {
        for (int i = 0; i < 10000000; i++) {
            lock.lock();    // 加锁
            lock.lock();    // 加锁
            try {
                count++;
            } finally {
                lock.unlock();  // 必须手动释放锁
                lock.unlock();  // 必须手动释放锁
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        D2_ReentrantLock service = new D2_ReentrantLock();
        Thread th1 = new Thread(service);
        Thread th2 = new Thread(service);

        th1.start();
        th2.start();

        th1.join();
        th2.join();

        System.out.println(count);
    }
}
