package com.std.video.geyiming.tec5;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author zhaojy
 * @date 2017-12-25
 */
public class D7_ReadWriteLock implements Runnable {
    final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    final ReentrantReadWriteLock.ReadLock readLock = lock.readLock();   // 获取只读锁
    final ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();    // 获取可写锁

    @Override
    public void run() {
        readLock.lock();
        writeLock.lock();
        readLock.unlock();
        writeLock.unlock();
    }
}
