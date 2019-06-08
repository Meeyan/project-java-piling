package com.std.yeziyuan;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用Lock来保证线程安全性
 *
 * @author zhaojy
 * @date 2019-06-06
 */
public class L020_LockDemo {

    private static int value = 0;

    /**
     * 默认为非公平锁，可以通过构造函数实现公平锁
     */
    private Lock lock = new ReentrantLock();

    /**
     * synchronized 修饰普通方法
     */
    public int getNext() {
        // 加锁
        lock.lock();
        value++;
        // 释放锁
        lock.unlock();
        return value;
    }


    public static void main(String[] args) {
        L020_LockDemo lockDemo = new L020_LockDemo();

        new Thread(() -> {
            for (int i = 0; i < 200; i++) {
                System.out.println(Thread.currentThread().getName() + "--" + lockDemo.getNext());
            }
        }).start();


        new Thread(() -> {
            for (int i = 0; i < 200; i++) {
                System.out.println(Thread.currentThread().getName() + "--" + lockDemo.getNext());
            }
        }).start();
    }
}
