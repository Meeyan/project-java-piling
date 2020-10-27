package com.demo.advice_150;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 生产者-消费者模式
 *
 * @author zhaojy
 * @date 2020/10/26 2:21 下午
 */
public class Depot {

    /**
     * 临时容量：缓存值
     */
    private int size;

    /**
     * 总容量
     */
    private int capacity;

    private Lock lock;
    private Condition fullCondition;
    private Condition emptyCondition;

    public Depot(int capacity) {
        this.capacity = capacity;
        this.lock = new ReentrantLock();
        this.fullCondition = lock.newCondition();
        this.emptyCondition = lock.newCondition();
    }

    /**
     * 生产者
     *
     * @param no int
     */
    public void produce(int no) {
        lock.lock();
        int produceLeft = no;
        try {
            while (produceLeft > 0) {
                // 当可用
                while (size >= capacity) {
                    System.out.println(Thread.currentThread() + " before wait");
                    fullCondition.await();
                    System.out.println(Thread.currentThread() + " after wait");
                }

                int inc = (produceLeft + size) > capacity ? (capacity - size) : produceLeft;
                produceLeft -= inc;

                size += inc;

                System.out.println("produce = " + inc + " , size = " + size);
                emptyCondition.signal();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void consume(int no) {
        lock.lock();
        try {
            int consumeLeft = no;
            while (consumeLeft > 0) {
                while (size <= 0) {
                    System.out.println(Thread.currentThread() + " before await");
                    emptyCondition.await();
                    System.out.println(Thread.currentThread() + " after await");
                }
                int dec = (size - consumeLeft) > 0 ? consumeLeft : size;
                consumeLeft -= dec;

                size -= dec;

                System.out.println("consume = " + dec + ", size = " + size);

                fullCondition.signal();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        Depot depot = new Depot(500);
        new Producer(depot).produce(500);
        new Producer(depot).produce(200);
        new Consumer(depot).consume(500);
        new Consumer(depot).consume(200);
    }
}

class Consumer {
    private Depot depot;

    public Consumer(Depot depot) {
        this.depot = depot;
    }

    public void consume(int no) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                depot.consume(no);
            }
        }, no + " consume thread").start();
    }
}

class Producer {
    private Depot depot;

    public Producer(Depot depot) {
        this.depot = depot;
    }

    public void produce(int no) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                depot.produce(no);
            }
        }, no + " produce thread").start();
    }
}