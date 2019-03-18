package com.std.video.geyiming.tec6;

import java.util.concurrent.*;

/**
 * 任务拒绝 : 拒绝策略？
 *
 * @author zhaojy
 * @date 2017-12-27
 */
public class D4_RejectThreadPoolDemo {
    static class MyTask implements Runnable {
        String name;

        public MyTask(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            System.out.println(System.currentTimeMillis() / 1000 + ", Thread Name:" + name + ": Thread Id:" + Thread.currentThread().getId());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 5, 0L, TimeUnit.MILLISECONDS, new SynchronousQueue<Runnable>(), Executors.defaultThreadFactory(),
                new RejectedExecutionHandler() {
                    @Override
                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                        System.out.println(r.toString() + " is rejected..");
                    }
                });
        for (int i = 0; i < 1000; i++) {
            MyTask task = new MyTask("Task Name - " + i);
            threadPoolExecutor.submit(task);
            Thread.sleep(10);
        }
    }

}
