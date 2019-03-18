package com.std.video.geyiming.tec6;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池扩展:捕获线程池工作状态的信息
 *
 * @author zhaojy
 * @date 2017-12-27
 */
public class D3_ExtThreadPool {
    static class MyThread implements Runnable {
        String name;

        public MyThread(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            System.out.println("running:" + Thread.currentThread().getId() + ",Task Name:" + name);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 5, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<Runnable>()) {
            @Override
            protected void beforeExecute(Thread t, Runnable r) {
                System.out.println("ready to run:" + ((MyThread) r).name);
            }

            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                System.out.println("thread finish:" + ((MyThread) r).name);
            }

            @Override
            protected void terminated() {
                System.out.println("thread terminate");
            }
        };

        for (int i = 0; i < 5; i++) {
            MyThread mt = new MyThread("Task-name-" + i);
            threadPoolExecutor.execute(mt);
            Thread.sleep(20);
        }

        threadPoolExecutor.shutdown();
    }
}
