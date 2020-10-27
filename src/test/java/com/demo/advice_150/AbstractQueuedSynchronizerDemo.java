package com.demo.advice_150;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zhaojy
 * @date 2020/10/26 1:58 下午
 */
public class AbstractQueuedSynchronizerDemo {

    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        MyThread t1 = new MyThread("t1", lock);
        MyThread t2 = new MyThread("t2", lock);
        t1.start();
        t2.start();
    }
}

class MyThread extends Thread {

    private Lock lock;

    public MyThread(String name, Lock lock) {
        super(name);
        this.lock = lock;
    }

    @Override
    public void run() {
        lock.lock();
        try {
            Thread.sleep(1000L);
            System.out.println(Thread.currentThread() + " running");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
