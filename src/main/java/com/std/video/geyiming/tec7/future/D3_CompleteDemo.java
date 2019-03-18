package com.std.video.geyiming.tec7.future;


import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * CompletableFuture demo - 2
 * 参考：https://www.jianshu.com/p/6f3ee90ab7d3
 *
 * @author zhaojy
 * @date 2018-01-24
 */
public class D3_CompleteDemo {
    public static void main(String[] args) throws InterruptedException {
        ;
    }

    /**
     * thenRun 方法演示
     *
     * @throws InterruptedException
     */
    @Test
    public void testThenRun() throws InterruptedException {
        CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("finish running");
            return "hello";
        }).thenRun(() -> {
            System.out.println("-hello world");
        });

        // 没有这个，无法打印。
        while (true) {

        }
    }

    /**
     * thenAcceptBoth 处理的CompletionStage都必须完成。
     */
    @Test
    public void thenAcceptBoth() throws InterruptedException {
        CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello ";
        }).thenAcceptBoth(CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(5100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "world";
        }), (ret1, ret2) -> {
            System.out.println(ret1 + " " + ret2);
        });

        Thread.sleep(5000);
    }

    /**
     * runAfterBoth测试。
     * 不关心前面的结果，只关心有没有执行完
     */
    @Test
    public void testRunAfterBoth() {
        long startTime = System.currentTimeMillis();
        CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "ret_1";
        }).runAfterBothAsync(CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "ret_2";
        }), () -> {
            System.out.println("process time :" + (System.currentTimeMillis() - startTime));
            System.out.println("final process");
        });

        while (true) {

        }
    }

    /**
     * applyToEither：传入一个CompletionStage，同时和之前的CompletionStage做对比，选取最快的结果
     */
    @Test
    public void testApplyToEither() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("step 1 running...");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("step 1 running");
            return "ret 1";
        }).applyToEither(CompletableFuture.supplyAsync(() -> {
            System.out.println("step 2 running...");
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "ret 2";
        }), s -> s).applyToEither(CompletableFuture.supplyAsync(() -> {
            System.out.println("step 3 running...");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "ret 3";
        }), s2 -> s2);


        while (true) {
            if (completableFuture.isDone()) {
                String retStr = completableFuture.get();
                System.out.println(retStr);
                break;
            }
        }
    }

    /**
     * acceptEither : 两个CompletionStage，谁计算的快，用谁的结果。
     *
     * @throws InterruptedException
     */
    @Test
    public void testAcceptEither() throws InterruptedException {
        CompletableFuture<Void> cFuture = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "ret 1 ..";
        }).acceptEither(CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "ret 2 ..";
        }), (finalRet) -> {
            System.out.println("final return : " + finalRet);
        });

        while (true) {
            if (cFuture.isDone()) {
                // 没有效果哦
                System.out.println("是不是阻塞了主线程??");
                break;
            }
        }

        System.out.println("finish running..");
    }

    /**
     * runAfterEither : 任何一个CompletionStage完成后，继续进行下一步操作，但是不接受上面的结果（入参为线程）。
     */
    @Test
    public void testRunAfterEither() throws InterruptedException {
        CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "ret 1";
        }).runAfterEither(CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "ret 2 ..";

        }), () -> {
            System.out.println("finish running");
        });
        Thread.sleep(5000);
    }

    /**
     * exceptionally ： 异常运行
     */
    @Test
    public void testExceptionally() throws InterruptedException {
        CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "right ret";
        }).runAfterEitherAsync(CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1500);

            } catch (InterruptedException e) {

            }
            if (true)
                throw new RuntimeException("挂啦");
            return "wrong ret";
        }), () -> {
        }).exceptionally(exception -> {
            System.out.println(exception.getMessage());
            return null;
        });

        Thread.sleep(3000);

        System.out.println("finish");
    }


    /**
     * whenComplete ： 测试
     *
     * @throws InterruptedException
     */
    @Test
    public void testWhenComplete() throws InterruptedException {
        CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (true)
                throw new RuntimeException("error happens");

            return "ret 1..";
        }).whenComplete((retValue, error) -> {
            System.out.println("value is :" + retValue);
            System.out.println("normal err is :" + error.getMessage());
        }).exceptionally(errorV -> {
            // todo 正常情况下，怎么监听到了这里？
            if (null != errorV) {
                System.out.println("error:" + errorV.getLocalizedMessage());
            }
            return "hahah";
        });

        Thread.sleep(4000);
    }

    /**
     * 测试handle：
     */
    @Test
    public void testHandle() throws ExecutionException, InterruptedException {
        CompletableFuture<String> handle = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (true) {
                throw new RuntimeException("error happens");
            }

            return "ret 11..";
        }).handle((value, error) -> {
            System.out.println("handle value is :" + value);
            System.out.println("handle error is :" + error.getMessage());
            return "new return";
        });

        Thread.sleep(5000);

        System.out.println(handle.get());
    }

}
