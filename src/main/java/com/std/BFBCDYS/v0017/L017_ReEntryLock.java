package com.std.BFBCDYS.v0017;

/**
 * 理解重入锁
 *
 * @author zhaojy
 * @date 2019-06-06
 */
public class L017_ReEntryLock implements Runnable {

    /**
     * 外层方法加锁
     */
    public synchronized void methodA() {
        System.out.println(Thread.currentThread().getName() + "---this is method A");

        // 可以直接进入：锁的可重入性质
        methodB();
    }

    /**
     * 内层方法加锁
     */
    public synchronized void methodB() {
        System.out.println(Thread.currentThread().getName() + "---this is method B");
    }

    @Override
    public void run() {
        methodA();
    }

    public static void main(String[] args) {
        /*L017_ReEntryLock shareObj = new L017_ReEntryLock();
        new Thread(shareObj).start();
        new Thread(shareObj).start();
        new Thread(shareObj).start();
        new Thread(shareObj).start();
        new Thread(shareObj).start();*/

        // 必须是同一个对象
        DealLockDemo lockDemo = new DealLockDemo();
        new Thread(new Runnable() {
            @Override
            public void run() {
                lockDemo.methodA();
            }
        }).start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                lockDemo.methodB();
            }
        }).start();

    }
}

/**
 * 演示死锁
 */
class DealLockDemo {
    private Object lockA = new Object();
    private Object lockB = new Object();

    public void methodA() {
        synchronized (lockA) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lockB) {
                System.out.println("method A");
            }
        }
    }

    public void methodB() {
        synchronized (lockB) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lockA) {
                System.out.println("method B");
            }
        }
    }
}
