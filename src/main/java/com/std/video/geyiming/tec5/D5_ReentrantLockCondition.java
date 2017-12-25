package com.std.video.geyiming.tec5;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 演示使用Condition，可等待，可唤醒
 *
 * @author zhaojy
 * @createTime 2017-12-25
 */
public class D5_ReentrantLockCondition implements Runnable {

    static ReentrantLock lock = new ReentrantLock();
    static Condition condition = lock.newCondition();

    @Override
    public void run() {
        try {
            lock.lock();
            System.out.println("----thread will be waiting..");
            condition.await();  // 通知当前线程进入wait状态并且释放锁，直到被signal、signalAll或interrupt
            System.out.println("----thread is continuing..");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        D5_ReentrantLockCondition sv = new D5_ReentrantLockCondition();
        Thread thread = new Thread(sv);
        thread.start();
        Thread.sleep(2000);
        lock.lock();
        condition.signal(); // 唤起处于await状态的线程。
        lock.unlock();
    }
}
