package com.std.video.geyiming.tec7.future;

import java.util.concurrent.*;

/**
 * Future模式的执行路径：使用线程池方式完成
 *
 * @author zhaojy
 * @date 2018-01-24
 */
public class D1_FutureDemo {

    /**
     * 异步计算任务，带返回值
     */
    static class SumTask implements Callable<Integer> {
        int a;
        int b;

        public SumTask(int a, int b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public Integer call() throws Exception {
            System.out.println(Thread.currentThread().getName());
            return a + b;
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 第一步：搞一个线程池
        ExecutorService service = Executors.newSingleThreadExecutor();

        // 第二步：弄一个异步计算任务
        SumTask sumTask = new SumTask(8, 10);

        // 第三步：获取Future对象，
        Future<Integer> future = service.submit(sumTask);
        service.shutdown();

        // 第四步：判断Future是否完成
        while (true) {
            if (future.isDone()) {
                break;
            }
        }

        System.out.println("求和结果：" + future.get());
    }
}
