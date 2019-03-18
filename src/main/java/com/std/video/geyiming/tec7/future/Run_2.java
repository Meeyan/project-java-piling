package com.std.video.geyiming.tec7.future;

import java.util.concurrent.*;

/**
 * @author zhaojy
 * @date 2017-12-28
 */
public class Run_2 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 构造TutureTask
        FutureTask<String> future = new FutureTask<String>(new RealDataNew("ab"));
        ExecutorService executorService = Executors.newFixedThreadPool(1);

        // 执行FutureTask，相当于上例中的client.request("a"); 发送请求
        // 开启线程，进行RealData的call执行
        executorService.submit(future);

        System.out.println("请求完毕..");

        // 额外操作，模拟其他业务逻辑处理
        Thread.sleep(2000);

        // 相当于data.getResult(),取得call()方法的返回值
        // 如果此时call()方法没有执行完，则依然会等待
        System.out.println("数据=" + future.get());
    }

    /**
     * 方法进一步改进
     *
     * @throws InterruptedException
     */
    public void futureMain_2() throws InterruptedException, ExecutionException {
        // 不需要构建FutureTask
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Future<String> ac = executorService.submit(new RealDataNew("ac"));
        System.out.println("请求完毕..");

        // 额外操作，模拟其他业务逻辑处理
        Thread.sleep(2000);

        // 相当于data.getResult(),取得call()方法的返回值
        // 如果此时call()方法没有执行完，则依然会等待
        System.out.println("数据=" + ac.get());
    }
}
