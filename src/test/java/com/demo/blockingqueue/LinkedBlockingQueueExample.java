package com.demo.blockingqueue;

import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * LinkedBlockingQueue 示例
 *
 * @author zhaojy
 * @date 2020/11/20 3:39 下午
 */
public class LinkedBlockingQueueExample {
    public static void main(String[] args) {

        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();

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

    static void consumer(LinkedBlockingQueue<String> queue) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        // poll 不阻塞，take 方法阻塞
                        String poll = queue.take();
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

    static void producer(LinkedBlockingQueue<String> queue) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        System.out.println("--搞一个");
                        queue.put(System.currentTimeMillis() + " - " + new Random().nextInt() + "");
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }
}

