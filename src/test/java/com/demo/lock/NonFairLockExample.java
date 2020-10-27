package com.demo.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 非公平所示例
 *
 * @author zhaojy
 * @date 2020/10/27 5:05 下午
 */
public class NonFairLockExample {

    /**
     * 示例线程
     */
    static class ExampleThread extends Thread {
        private Lock lock;

        public ExampleThread(String name, Lock lock) {
            super(name);
            this.lock = lock;
        }

        @Override
        public void run() {
            lock.lock();
            try {
                System.out.println(Thread.currentThread() + " running");
                Thread.sleep(1000L);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        Lock lock = new ReentrantLock(true);
        ExampleThread t1 = new ExampleThread("t1", lock);
        ExampleThread t2 = new ExampleThread("t2", lock);
        ExampleThread t3 = new ExampleThread("t3", lock);

        t1.start();
        t3.start();
        t2.start();
    }
}
