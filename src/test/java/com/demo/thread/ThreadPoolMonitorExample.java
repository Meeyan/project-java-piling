package com.demo.thread;

import java.util.concurrent.*;

/**
 * @author zhaojy
 * @date 2020/12/3 2:16 下午
 */
public class ThreadPoolMonitorExample {
    public static void main(String[] args) throws InterruptedException {
        RejectedExecutionHandler rejectedExecutionHandler = new RejectedExecutionHandlerImpl();

        ThreadFactory threadFactory = Executors.defaultThreadFactory();

        // 自己搞一个线程池
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 4, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(2), threadFactory, rejectedExecutionHandler);

        // 监视器
        ThreadPoolMonitor monitor = new ThreadPoolMonitor(threadPoolExecutor, 3);

        Thread thread = new Thread(monitor);
        thread.start();

        for (int i = 0; i < 10; i++) {
            threadPoolExecutor.execute(new WorkThread("cmd-" + i));
        }

        Thread.sleep(30000);
        //shut down the pool
        threadPoolExecutor.shutdown();

        //shut down the monitor thread
        Thread.sleep(5000);
        monitor.shutdown();


    }
}
