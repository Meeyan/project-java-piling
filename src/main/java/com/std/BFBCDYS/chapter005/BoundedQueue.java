package com.std.BFBCDYS.chapter005;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 模拟有界队列
 * 1. 考虑过使用list来完成，但是，list本身的变动会带来数组的频繁拷贝移动，性能待评估。
 * 2. 使用这种模式，在并发量大的时候，会出现大量的阻塞情况
 *
 * @author zhaojy
 * @date 2019-07-04 10:22
 */
public class BoundedQueue<T> {


    private static Logger logger = LoggerFactory.getLogger(BoundedQueue.class);

    private Object[] items;

    private int addIndex, removeIndex, count;

    private Lock lock = new ReentrantLock();

    private Condition notEmpty = lock.newCondition();

    private Condition notFull = lock.newCondition();

    public BoundedQueue(int size) {
        items = new Object[size];
    }

    public void add(T t) {
        lock.lock();
        try {

            // 满了，就等待
            while (count == items.length) {
                notFull.await();
            }

            items[addIndex] = t;

            // 准备下一个槽位
            if (++addIndex == items.length) {
                addIndex = 0;
            }

            ++count;
            logger.info("add one");
            notEmpty.signal();
        } catch (InterruptedException e) {
            logger.error("error happens:", e);
        } finally {
            lock.unlock();
        }
    }

    public T remove() {
        lock.lock();
        try {
            // 空了，就等待
            while (count == 0) {
                notEmpty.await();
            }

            Object x = items[removeIndex];

            // 准备从头开始
            if (++removeIndex == items.length) {
                removeIndex = 0;
            }
            count--;
            notFull.signal();

            logger.info("remove one");
            return (T) x;
        } catch (InterruptedException e) {
            logger.error("error happens:", e);
        } finally {
            lock.unlock();
        }

        return null;
    }

    public static void main(String[] args) {
        BoundedQueue<Object> queue = new BoundedQueue<>(10);

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (; ; ) {
                    queue.add(new Object());
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (; ; ) {
                    queue.remove();
                }
            }
        }).start();
    }

}
