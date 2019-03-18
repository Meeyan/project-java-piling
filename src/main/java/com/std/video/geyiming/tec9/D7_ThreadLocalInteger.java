package com.std.video.geyiming.tec9;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 使用ThreadLocal的基本示例 ，各个线程，对自己的变量做累加操作，互不影响
 *
 * @author zhaojy
 * @date 2018-01-15
 */
public class D7_ThreadLocalInteger {
    private static ThreadLocal<Integer> countTl = new ThreadLocal<Integer>() {
        @Override
        protected Integer initialValue() {
            return 0;
        }
    };

    static class CounterThread implements Runnable {
        int index;

        public CounterThread(int index) {
            this.index = index;
        }

        @Override
        public void run() {
            System.out.println("线程索引:" + index + ",初始值：" + countTl.get());
            for (int i = 0; i < 100; i++) {
                countTl.set(countTl.get() + 1);
            }
            System.out.println("线程索引:" + index + ",最终值:" + countTl.get());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService esc = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            esc.execute(new CounterThread(i));
        }
    }

}
