package com.std.video.geyiming.tec7.future;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 讲解Future-task模式
 * 来源：https://www.cnblogs.com/cz123/p/7693064.html
 *
 * @author zhaojy
 * @create-time 2018-01-22
 */
public class D2_FutureCook {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long startTime = System.currentTimeMillis();

        // 第一步：构造异步执行任务
        BuyChuju buyChuju = new BuyChuju();
        FutureTask<Chuju> buyChuJuTask = new FutureTask<Chuju>(buyChuju);

        // 第二步：开启任务
        new Thread(buyChuJuTask).start();   // 开启线程买厨具

        ShiCai shiCai = new ShiCai();   // 食材到位
        System.out.println("第二步食材到位..");

        if (buyChuJuTask.isDone()) {
            System.out.println("第三步：厨具还没到，心情好就等着（心情不好就调用cancel方法取消订单）");
        }

        // 轮询遍历
        Chuju chuju;
        while (true) {
            if (buyChuJuTask.isDone()) {
                chuju = buyChuJuTask.get();
                break;
            }
            Thread.sleep(100);
        }
        cook(chuju, shiCai);
        System.out.println("总共用时" + (System.currentTimeMillis() - startTime) + "ms");
    }


    /*
       * 将网上购买厨具的动作改为异步执行
       */
    static class BuyChuju implements Callable<Chuju> {

        @Override
        public Chuju call() throws Exception {
            System.out.println("第一步：下单..");
            System.out.println("第一步：等待送货..");
            Thread.sleep(5000); // 模拟送货时间
            System.out.println("第一步：快递送到..");
            return new Chuju();
        }
    }

    static void cook(Chuju chuju, ShiCai shicai) {
        System.out.println("第四步：齐活儿，开始做饭..");
    }

    static class Chuju {

    }

    // 食材类
    static class ShiCai {

    }
}
