package com.demo.collection;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 双端队列   ArrayBlockingQueue
 * 1. 有上限，上限在 queue 初始化时指定
 * 2. 会阻塞 put 和 take 方法会阻塞
 *
 * @author zhaojy
 * @date 2020-11-15 23:11
 */
public class BlockingQueueExample {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(1024);

        QueueProducer producer = new QueueProducer(queue);
        QueueConsumer consumer = new QueueConsumer(queue);

        producer.start();
        consumer.start();
        Thread.sleep(40000);
    }
}

class QueueProducer extends Thread {
    private BlockingQueue<String> queue;

    public QueueProducer(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            queue.put("1");
            Thread.sleep(1000);
            queue.put("2");
            Thread.sleep(1000);
            queue.put("3");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class QueueConsumer extends Thread {

    private BlockingQueue<String> queue;

    public QueueConsumer(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            System.out.println(queue.take());
            System.out.println(queue.take());
            System.out.println(queue.take());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}