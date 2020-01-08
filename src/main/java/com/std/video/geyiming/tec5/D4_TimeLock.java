package com.std.video.geyiming.tec5;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 可限时特性示例
 *
 * @author zhaojy
 * @date 2017-12-25
 */
public class D4_TimeLock implements Runnable {
    static ReentrantLock lock = new ReentrantLock();

    @Override
    public void run() {
        try {
            // 限时5秒钟，指定时间单位
            if (lock.tryLock(5, TimeUnit.SECONDS)) {
                System.out.println("----get lock success..");
                Thread.sleep(6000);
            } else {
                System.out.println("----get lock failed..");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();  // 释放锁
            }
        }
    }

    public static void main(String[] args) {
        D4_TimeLock dt1 = new D4_TimeLock();
        Thread t1 = new Thread(dt1);
        Thread t2 = new Thread(dt1);
        t1.start();
        t2.start();
    }
}
