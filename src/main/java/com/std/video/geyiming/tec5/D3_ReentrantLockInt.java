package com.std.video.geyiming.tec5;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 重入锁demo3：
 * 讲解锁被中断
 *
 * @author zhaojy
 * @date 2017-07-27
 */
public class D3_ReentrantLockInt implements Runnable {

    // 注意static关键字：两把锁都是全局锁，
    static ReentrantLock lock1 = new ReentrantLock();
    static ReentrantLock lock2 = new ReentrantLock();

    int lock;

    // 控制加锁顺序，方便构造死锁
    public D3_ReentrantLockInt(int lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        try {
            // 下面模拟死锁
            if (lock == 1) {
                lock1.lockInterruptibly();  // 可中断加锁；如果是lock方法，不能中断；
                Thread.sleep(500);          // 500毫秒后，获取lock2
                lock2.lockInterruptibly();
            } else {
                lock2.lockInterruptibly();
                Thread.sleep(500);          // 500毫秒后，获取lock1
                lock1.lockInterruptibly();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (lock1.isHeldByCurrentThread()) {
                lock1.unlock();
            }

            if (lock2.isHeldByCurrentThread()) {
                lock2.unlock();
            }
            System.out.println(Thread.currentThread().getId() + "-线程退出");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        D3_ReentrantLockInt sv1 = new D3_ReentrantLockInt(1);
        D3_ReentrantLockInt sv2 = new D3_ReentrantLockInt(2);

        Thread th1 = new Thread(sv1);
        Thread th2 = new Thread(sv2);

        th1.start();
        th2.start();

        Thread.sleep(1000);

        DeadLockChecker.check();
    }
}
