package com.std.video.geyiming.tec4;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 非阻塞线程计数器
 * todo 经测试，性能也没有太大差别，啥情况？
 *
 * @author zhaojy
 * @createTime 2017-05-26
 */
public class NoBlockingCounter {
    private AtomicInteger counter = new AtomicInteger(0);

    private int count = 0;

    public Integer getCounter() {
        return counter.get();
    }

    public synchronized int getCount() {
        return this.count;
    }

    public synchronized void setCount() {
        // System.out.println(Thread.currentThread().getName() + "-----" + this.count);
        this.count++;
    }

    /**
     * 可以认为是线程安全的
     */
    public void increment() {
        // 保证一直自旋，每一次都必须成功
        for (; ; ) {
            int v = counter.get();
            boolean b = counter.compareAndSet(v, v + 1);    // 利用CAS保证安全从左
            // System.out.println(Thread.currentThread().getName() + "-----" + b);
            if (b) {
                break;
            }
        }
    }

    public static void main(String[] args) {
        final NoBlockingCounter noBlockingCounter = new NoBlockingCounter();

        /**
         * todo 采用CAS方式做阻塞式同步
         */
        List<Thread> safeTsList = new ArrayList<>();
        long timeStart_1 = System.currentTimeMillis();

        // 正确的值，肯定是4000，造线程
        for (int i = 0; i < 10000; i++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 200; j++) {
                        noBlockingCounter.increment();
                    }
                }
            });
            t.setName("Thread - safe -" + i);
            safeTsList.add(t);
        }

        // 启动线程
        for (Thread thread : safeTsList) {
            thread.start();
        }

        // 等线程完成
        for (Thread thread : safeTsList) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long timeEnd_1 = System.currentTimeMillis();
        System.out.println(noBlockingCounter.getCounter() + "-----" + (timeEnd_1 - timeStart_1));

        /**
         * todo 采用内部锁的方式实现
         */
        long timeStart_2 = System.currentTimeMillis();
        List<Thread> safeList_2 = new ArrayList<>();
        // 传统内部所方式试下 肯定是4000，造线程
        for (int i = 0; i < 10000; i++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 200; j++) {
                        noBlockingCounter.setCount();
                    }
                }
            });

            t.setName("Thread - unsafe - " + i);
            safeList_2.add(t);
        }

        // 启动线程
        for (Thread thread : safeList_2) {
            thread.start();
        }

        // 等线程完成
        for (Thread thread : safeList_2) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long timeEnd_2 = System.currentTimeMillis();
        System.out.println(noBlockingCounter.getCount() + "-----" + (timeEnd_2 - timeStart_2));


    }
}
