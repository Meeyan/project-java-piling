package com.demo.blockingqueue;

import java.util.Random;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * PriorityBlockingQueue 示例
 *
 * @author zhaojy
 * @date 2020/11/20 3:50 下午
 */
public class PriorityBlockingQueueExample {

    public static void main(String[] args) {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();

        // 生产者
        consumer(queue);

        // 消费者
        producer(queue);

        while (true) {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static void consumer(PriorityBlockingQueue<Integer> queue) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        // poll 不阻塞，take 方法阻塞
                        Integer poll = queue.take();
                        Thread.sleep(300);
                        System.out.println(System.currentTimeMillis() + " - " + poll);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        new Thread(() -> {
            while (true) {
                try {
                    System.out.println("queue size:" + queue.size());
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    static void producer(PriorityBlockingQueue<Integer> queue) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int count = 0;
                while (true) {
                    count++;
                    try {
                        queue.put(new Random().nextInt(60));
                        Thread.sleep(100);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (count == 100) {
                        break;
                    }
                }
            }
        }).start();

    }
}
