package com.demo.collection;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 模拟多线程下 ConcurrentLinkedQueue 的使用
 *
 * @author zhaojy
 * @date 2020/11/10 11:21 上午
 */
public class ConcurrentLinkedQueueExample {
    public static void main(String[] args) {
        ConcurrentLinkedQueue<Integer> clq = new ConcurrentLinkedQueue<>();
        QueuePutThread putThread = new QueuePutThread(clq);
        QueueGetThread getThread = new QueueGetThread(clq);
        putThread.start();
        getThread.start();
    }
}

class QueuePutThread extends Thread {

    private ConcurrentLinkedQueue<Integer> clq;

    public QueuePutThread(ConcurrentLinkedQueue<Integer> clq) {
        this.clq = clq;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("add " + i);
            clq.add(i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class QueueGetThread extends Thread {

    private ConcurrentLinkedQueue<Integer> clq;

    public QueueGetThread(ConcurrentLinkedQueue<Integer> clq) {
        this.clq = clq;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("poll " + clq.poll());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}