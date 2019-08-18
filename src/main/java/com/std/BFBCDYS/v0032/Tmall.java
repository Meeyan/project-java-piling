package com.std.BFBCDYS.v0032;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 模拟生产者消费者模式 - 商城
 * 使用condition模式实现
 *
 * @author zhaojy
 * @date 2019-08-18
 */
public class Tmall {
    private int count;

    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public final int MAX_COUNT = 10;

    public void push() {
        lock.lock();
        // 为什么此处要用while？
        while (count >= MAX_COUNT) {
            try {
                System.out.println("生产者库存数量达到上限");
                // 使用wait必须获取锁
                condition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        count++;
        System.out.println(Thread.currentThread().getName() + " 生产者生产,当前库存为：" + count);
        condition.signal();
        lock.unlock();
    }

    public void take() {
        lock.lock();
        while (count <= 0) {
            try {
                System.out.println(Thread.currentThread().getName() + " 库存数量为0，消费者等待。");
                condition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        count--;
        System.out.println(Thread.currentThread().getName() + " 消费者消费,当前库存为：" + count);
        condition.signal();
        lock.unlock();
    }

    public static void main(String[] args) {
        Tmall tmall = new Tmall();
        PushTarget pushTarget = new PushTarget(tmall);
        TakerTarget takerTarget = new TakerTarget(tmall);

        new Thread(pushTarget).start();
        new Thread(pushTarget).start();
        new Thread(takerTarget).start();

    }
}
