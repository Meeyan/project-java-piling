package com.std.BFBCDYS.v0031;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 要求：三个线程依次执行a，b，c
 * 使用：ReentrantLock、await、signal实现
 * 1. 创建锁
 * 2. 搞三个condition
 * 3. 依次await和signal
 *
 * @author zhaojy
 * @date 2019/8/15 23:54
 */
public class DemoOptimized {

    private int signal;

    private Lock lock = new ReentrantLock();
    private Condition conditionA = lock.newCondition();
    private Condition conditionB = lock.newCondition();
    private Condition conditionC = lock.newCondition();

    public void a() {
        lock.lock();
        while (signal != 0) {
            try {
                conditionA.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("a");
        signal++;
        conditionB.signal();
        lock.unlock();
    }

    public void b() {
        lock.lock();
        while (signal != 1) {
            try {
                conditionB.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("b");
        signal++;
        conditionC.signal();
        lock.unlock();
    }

    public void c() {
        lock.lock();
        while (signal != 2) {
            try {
                conditionC.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("c");
        System.out.println("");
        signal = 0;
        conditionA.signal();
        lock.unlock();
    }

    public static void main(String[] args) {
        DemoOptimized demo = new DemoOptimized();
        AA a = new AA(demo);
        BB b = new BB(demo);
        CC c = new CC(demo);
        new Thread(a).start();
        new Thread(b).start();
        new Thread(c).start();
    }
}

class AA implements Runnable {

    private DemoOptimized demo;

    public AA(DemoOptimized demo) {
        this.demo = demo;
    }

    @Override
    public void run() {
        while (true) {
            demo.a();
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


class BB implements Runnable {

    private DemoOptimized demo;

    public BB(DemoOptimized demo) {
        this.demo = demo;
    }

    @Override
    public void run() {
        while (true) {
            demo.b();
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class CC implements Runnable {

    private DemoOptimized demo;

    public CC(DemoOptimized demo) {
        this.demo = demo;
    }

    @Override
    public void run() {
        while (true) {
            demo.c();
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}