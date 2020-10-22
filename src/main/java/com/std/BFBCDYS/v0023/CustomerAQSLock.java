package com.std.BFBCDYS.v0023;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 自定义锁演示
 *
 * @author zhaojy
 * @date 2019-06-08
 */
public class CustomerAQSLock implements Lock {

    private CustomerLock lock;

    public CustomerAQSLock() {
        this.lock = new CustomerLock();
    }

    @Override
    public void lock() {
        lock.acquire(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        lock.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return lock.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return lock.tryAcquireNanos(1, unit.toNanos(time));
    }

    @Override
    public void unlock() {
        lock.release(1);
    }

    @Override
    public Condition newCondition() {
        return lock.getCondition();
    }

    /**
     * 自定义AQS锁对象
     */
    class CustomerLock extends AbstractQueuedSynchronizer {

        @Override
        protected boolean tryAcquire(int arg) {
            int state = getState();
            if (state == 0) {
                if (compareAndSetState(0, arg)) {
                    setExclusiveOwnerThread(Thread.currentThread());
                    return true;
                }
            }
            return false;
        }

        @Override
        protected boolean tryRelease(int arg) {
            if (Thread.currentThread() != getExclusiveOwnerThread()) {
                return false;
            }
            if (getState() == 0) {
                setExclusiveOwnerThread(null);
                return true;
            }
            int newState = getState() - arg;
            setState(newState);
            return true;
        }

        Condition getCondition() {
            return new ConditionObject();
        }
    }

}
