package com.std.BFBCDYS.chapter005;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 需求：
 * 设计一个锁，在同一时刻，至多允许两个现成同时访问，超过两个线程的将被阻塞
 *
 * @author zhaojy
 * @date 2019-07-01 17:48
 */
public class TwinsLock implements Lock {


    private static final class Sync extends AbstractQueuedSynchronizer {

        public Sync(int count) {
            if (count <= 0) {
                throw new IllegalArgumentException("count must by large than zero.");
            }

            // 设置state为初始值，重点
            setState(count);
        }

        /**
         * 书中给的例子，此处的for循环会占用大量的cpu资源
         * 可以优化为交给等待队列实现
         *
         * @param reduceCount int
         * @return int
         */
        @Override
        protected int tryAcquireShared(int reduceCount) {
            int current = getState();
            int newCount = current - reduceCount;
            if (newCount >= 0 && compareAndSetState(current, newCount)) {
                return newCount;
            }
            System.out.println(Thread.currentThread().getName() + "---- next acquire");
            return -1;
        }

        @Override
        protected boolean tryReleaseShared(int returnCount) {
            for (; ; ) {
                int current = getState();
                int newCount = current + returnCount;
                if (compareAndSetState(current, newCount)) {
                    return true;
                }
                // System.out.println(Thread.currentThread().getName() + "---- next release");
            }
        }

        public Condition getCondition() {
            return new ConditionObject();
        }
    }

    private Sync sync = new Sync(2);

    @Override
    public void lock() {
        sync.acquireShared(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        sync.releaseShared(1);
    }

    @Override
    public Condition newCondition() {
        return sync.getCondition();
    }
}
