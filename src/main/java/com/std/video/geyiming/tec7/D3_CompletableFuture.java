package com.std.video.geyiming.tec7;

import org.junit.Test;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * 讲解CompletableFuture
 * 来源：https://www.jianshu.com/p/4897ccdcb278
 *
 * @author zhaojy
 * @create-time 2018-01-25
 */
public class D3_CompletableFuture {

    /**
     * CompletableFuture运行模式-1
     *
     * @throws ExecutionException   Exception
     * @throws InterruptedException Exception
     */
    @Test
    public void run_1() throws ExecutionException, InterruptedException {
        CompletableFuture<String> cFuture = new CompletableFuture<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("task doing...");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                cFuture.complete("Result");
            }
        }).start();

        String retStr = cFuture.get();
        System.out.println("计算结果：" + retStr);

    }

    /**
     * 可以获取线程内部发生的异常
     *
     * @throws ExecutionException   Exception
     * @throws InterruptedException Exception
     */
    @Test
    public void run_2() throws ExecutionException, InterruptedException {
        CompletableFuture<String> cFuture = new CompletableFuture<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("task doing...");
                try {
                    Thread.sleep(3000);
                    throw new RuntimeException("挂了");
                } catch (InterruptedException e) {
                    cFuture.completeExceptionally(e);   // 使用CompletableFuture抛出异常
                }
            }
        }).start();

        String s = cFuture.get();
        System.out.println("结果2：" + s);
    }

    /**
     * 3. 使用工厂方法获取实例对象 - supplyAsync
     *
     * @throws ExecutionException   Exception
     * @throws InterruptedException Exception
     */
    @Test
    public void run_3() throws ExecutionException, InterruptedException {
        CompletableFuture<String> cFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("run_3 doing");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "run_3 result";
        });

        System.out.println("run3 结果:" + cFuture.get());
    }

    /**
     * 3.2 使用工厂方法：
     * anyOf：任意一个完成就会结束
     * allOf：全部完成才会结束
     *
     * @throws ExecutionException   ExecutionException
     * @throws InterruptedException InterruptedException
     */
    @Test
    public void run_3_2() throws ExecutionException, InterruptedException {
        // 第一个任务
        CompletableFuture<String> cFuture_1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("cFuture1 doing");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "cFuture result 1";
        });

        // 第二个任务
        CompletableFuture<String> cFuture_2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("cFuture2 doing");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "cFuture result 2";
        });

        // 任何一个完成，都算
        CompletableFuture<Object> anyFuture = CompletableFuture.anyOf(cFuture_1, cFuture_2);
        System.out.println("第一个完成任务的结果:" + anyFuture.get());

        CompletableFuture<Void> allFuture = CompletableFuture.allOf(cFuture_1, cFuture_2);

        // 这样会阻塞主线程
        while (true) {
            if (allFuture.isDone()) {
                break;
            }
            System.out.println("还没有完成呐。。。");
        }

        System.out.println("所有任务完成");
    }

    /**
     * 4. 合并CompletableFuture【相互之间存在依赖性】
     * 即：任务之间存在前后依赖关系
     *
     * @throws ExecutionException   ExecutionException
     * @throws InterruptedException InterruptedException
     */
    @Test
    public void run_4() throws ExecutionException, InterruptedException {
        // 合并CompletableFuture
        CompletableFuture<String> composeFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("run4 step 1 doing");
            System.out.println(Thread.currentThread().getName());
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("run4 step 1 finish");
            return " result 1";
        }).thenCompose(result -> CompletableFuture.supplyAsync(() -> {
            System.out.println("run4 step 2 doing.");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("run4 step 2 finish");
            return "final result:" + result + " ,result 2"; // 合并计算结果
        }));
        System.out.println("return result:" + composeFuture.get());
    }


    /**
     * 4.2 合并任务，任务之间相互独立，不存在依赖关系。
     *
     * @throws ExecutionException   ExecutionException
     * @throws InterruptedException InterruptedException
     */
    @Test
    public void run_4_2() throws ExecutionException, InterruptedException {

        // 第一个任务
        CompletableFuture<Integer> cFuture_1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("run_4_2 step1 doing");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 100;
        });

        // 第二个任务
        CompletableFuture<Integer> cFuture_2 = cFuture_1.thenCombine(
                CompletableFuture.supplyAsync(() -> {
                    System.out.println("run_4_2 step2 doing");
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return 300;
                }),
                (result1, result2) -> {
                    return result1 + result2;
                });

        System.out.println("最终结果:" + cFuture_2.get());

    }

    /**
     * 5. 事件监听，及任务合并
     *
     * @throws ExecutionException   Exception
     * @throws InterruptedException Exception
     */
    @Test
    public void run_5() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> task_1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("run 5 task 1 doing");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 3000;
        });

        // 监听结束事件
        task_1.thenAccept(result -> System.out.println("task1 finish, result:" + result));

        CompletableFuture<Integer> task_2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("run 5 task 2 doing");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 2800;
        });

        // 第二个完成事件监听
        task_2.thenAccept(result2 -> {
            System.out.println("task2 finish,result+" + result2);
        });

        // 任务合并
        CompletableFuture<Integer> task_3 = task_1.thenCombine(task_2,
                (result1, result2) -> {
                    System.out.println("final value sum doing..");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return result1 + result2;
                });

        System.out.println("final value:" + task_3.get());
    }

    private static Random rand = new Random();
    private static long t = System.currentTimeMillis();

    static int getMoreData() {
        System.out.println("begin to start compute");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("end to start compute. passed " + (System.currentTimeMillis() - t) / 1000 + " seconds");
        return rand.nextInt(1000);
    }

    public static void main(String[] args) throws Exception {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(D3_CompletableFuture::getMoreData);
        Future<Integer> f = future.whenComplete((v, e) -> {
            System.out.println(v);
            System.out.println(e);
        });
        System.out.println(f.get());
    }
}
