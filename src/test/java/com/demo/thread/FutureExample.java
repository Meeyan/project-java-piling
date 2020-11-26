package com.demo.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Future 示例
 *
 * @author zhaojy
 * @date 2020/11/20 6:26 下午
 */
public class FutureExample {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<Integer> future = executorService.submit(new Callable<Integer>() {
            @Override
            public Integer call() {
                long start = System.currentTimeMillis();
                while (true) {
                    long current = System.currentTimeMillis();
                    if (current - start > 1000) {
                        return 1;
                    }
                }
            }
        });

        try {
            Integer integer = future.get();
            System.out.println(integer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
