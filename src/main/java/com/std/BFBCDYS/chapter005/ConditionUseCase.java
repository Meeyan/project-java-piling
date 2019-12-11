package com.std.BFBCDYS.chapter005;

import lombok.extern.log4j.Log4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Condition使用示例
 *
 * @author zhaojy
 * @date 2019-07-04 09:52
 */
@Log4j
public class ConditionUseCase {

    private Lock lock = new ReentrantLock();

    /**
     * 必须先搞到锁
     */
    private Condition condition = lock.newCondition();

    public void conditionWait() {
        while (true) {
            lock.lock();
            try {
                Thread.sleep(1000L);
                log.info("await");
                // 调用await方法后，将会释放锁，同时线程阻塞，被唤醒后，将会再次竞争锁
                condition.await();
                log.info("------be awake");
            } catch (Exception e) {
                log.error("error happens:", e);
            } finally {
                lock.unlock();
            }
        }

    }

    public void conditionSignal() {

        while (true) {

            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                log.error("error happens:", e);
            }

            lock.lock();
            try {

                log.info("++++++++notify");
                // 随机唤醒一个等待线程
                condition.signal();
            } catch (Exception e) {
                log.error("error happens:", e);
            } finally {
                lock.unlock();
            }
        }

    }

    public static void main(String[] args) {
        ConditionUseCase conditionUseCase = new ConditionUseCase();
        new Thread(() -> conditionUseCase.conditionWait()).start();

        new Thread(() -> conditionUseCase.conditionSignal()).start();
    }

}
