package com.demo.lock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁示例
 *
 * @author zhaojy
 * @date 2020/10/28 6:06 下午
 */
public class ReadWriteLockExample {
    public static void main(String[] args) {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        ReadThread rth1 = new ReadThread("rth1", lock);
        ReadThread rth2 = new ReadThread("rth2", lock);
        WriteThread wth1 = new WriteThread("wth1", lock);

        rth1.start();
        rth2.start();
        wth1.start();
    }
}

/**
 * 读锁
 */
class ReadThread extends Thread {

    private ReentrantReadWriteLock readWriteLock;

    public ReadThread(String name, ReentrantReadWriteLock readWriteLock) {
        super(name);
        this.readWriteLock = readWriteLock;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " trying to lock");

        try {
            readWriteLock.readLock().lock();
            System.out.println(Thread.currentThread().getName() + " lock successfully");
            Thread.sleep(5000L);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            readWriteLock.readLock().unlock();
            System.out.println(Thread.currentThread().getName() + " unlock successfully");
        }
    }
}

/**
 * 读锁
 */
class WriteThread extends Thread {

    private ReentrantReadWriteLock readWriteLock;

    public WriteThread(String name, ReentrantReadWriteLock readWriteLock) {
        super(name);
        this.readWriteLock = readWriteLock;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " trying to lock");
        try {
            readWriteLock.writeLock().lock();
            System.out.println(Thread.currentThread().getName() + " lock successfully");
            Thread.sleep(5000L);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            readWriteLock.writeLock().unlock();
            System.out.println(Thread.currentThread().getName() + " unlock successfully");
        }
    }
}