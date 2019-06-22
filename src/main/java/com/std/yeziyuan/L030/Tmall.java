package com.std.yeziyuan.L030;

/**
 * 模拟商城
 *
 * @author zhaojy
 * @date 2019-06-22 15:49
 */
public class Tmall {

    /**
     * 库存数量
     */
    private volatile int count = 0;

    /**
     * 生产者线程
     */
    public synchronized void push() {

        // 此处完全没有必要使用wait/notify
        /*if (count >= 10) {
            // 等待
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/

        if (count >= 10) {
            return;
        }

        int oriCount = count;

        count++;

        System.out.println(Thread.currentThread().getName() + " 生产一个商品，原来的库存：" + oriCount + ",新库存：" + count);

        // 唤醒一个消费者
        notify();
    }

    /**
     * 消费者线程
     */
    public synchronized void take() {

        // 此处完全没有必要使用wait/notify
        /*if (count <= 0) {
            // 等待
            try {

                notifyAll();

                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/

        if (count <= 0) {
            return;
        }

        int oriCount = count;
        count--;
        System.out.println(Thread.currentThread().getName() + " 消费一个商品，原来的库存为：" + oriCount + ",剩余库存：" + count);
    }
}
