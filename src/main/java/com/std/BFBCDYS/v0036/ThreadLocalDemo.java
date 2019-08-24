package com.std.BFBCDYS.v0036;

/**
 * 演示使用ThreadLocal
 * 各个线程之间互不影响
 * 那么：ThreadLocal是怎么做到的呢？
 *
 * @author zhaojy
 * @date 2019-08-24 15:29
 */
public class ThreadLocalDemo {
    private ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>() {
        @Override
        protected Integer initialValue() {
            return 0;
        }
    };

    public Integer getNext() {
        Integer value = threadLocal.get();
        value++;
        threadLocal.set(value);
        return value;
    }

    public static void main(String[] args) {
        ThreadLocalDemo localDemo = new ThreadLocalDemo();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (; ; ) {
                    System.out.println(Thread.currentThread().getName() + "--" + localDemo.getNext());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (; ; ) {
                    System.out.println(Thread.currentThread().getName() + "--" + localDemo.getNext());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (; ; ) {
                    System.out.println(Thread.currentThread().getName() + "--" + localDemo.getNext());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (; ; ) {
                    System.out.println(Thread.currentThread().getName() + "--" + localDemo.getNext());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }
}
