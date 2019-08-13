package com.std.BFBCDYS.v0015;

/**
 * 模拟生产者消费者模式 - 商城
 *
 * @author zhaojy
 * @date 2019-08-13
 */
public class Tmall {
    private int count;

    public final int MAX_COUNT = 10;

    public synchronized void push() {
        // 为什么此处要用while？
        while (count >= MAX_COUNT) {
            try {
                System.out.println("生产者库存数量达到上限");
                // 使用wait必须获取锁
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        count++;
        System.out.println(Thread.currentThread().getName() + " 生产者生产,当前库存为：" + count);
        notifyAll();
    }

    public synchronized void take() {
        while (count <= 0) {
            try {
                System.out.println(Thread.currentThread().getName() + " 库存数量为0，消费者等待。");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        count--;
        System.out.println(Thread.currentThread().getName() + " 消费者消费,当前库存为：" + count);
        notifyAll();
    }
}
