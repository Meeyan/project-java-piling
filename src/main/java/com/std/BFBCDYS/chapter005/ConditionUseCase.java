package com.std.BFBCDYS.chapter005;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Condition使用示例
 *
 * @author zhaojy
 * @date 2019-07-04 09:52
 */
public class ConditionUseCase {

    private Lock lock = new ReentrantLock();

    /**
     * 必须先搞到锁
     */
    private Condition condition = lock.newCondition();

    public void conditionWait() {
        lock.lock();
        try {
            System.out.println("await");

            // 调用await方法后，将会释放锁，同时线程阻塞，被唤醒后，将会再次竞争锁
            condition.await();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void conditionSignal() {
        lock.lock();
        try {
            // 随机唤醒一个等待线程
            condition.signal();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        ConditionUseCase conditionUseCase = new ConditionUseCase();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (; ; ) {
                    conditionUseCase.conditionWait();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (; ; ) {
                    conditionUseCase.conditionSignal();
                }
            }
        }).start();
    }

}
