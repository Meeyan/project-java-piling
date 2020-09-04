package com.std.deep_understand_jvm.aiguigu.ch08;

/**
 * 栈上分配测试
 * -Xmx1G -Xms1G -XX:-DoEscapeAnalysis -XX:+PrintGCDetails
 * 测试方法分为如下4个步骤：
 * 1. 执行参数：-Xmx1G -Xms1G -XX:-DoEscapeAnalysis -XX:+PrintGCDetails，观察耗时和 visualvm 中的内存抽样
 * 2. 执行参数：-Xmx1G -Xms1G -XX:+DoEscapeAnalysis -XX:+PrintGCDetails，观察耗时和 visualvm 中的内存抽样
 * 3. 执行参数：-Xmx256m -Xms256m -XX:-DoEscapeAnalysis -XX:+PrintGCDetails，观察耗时和 visualvm 中的内存抽样
 * 4. 执行参数：-Xmx256m -Xms256m -XX:+DoEscapeAnalysis -XX:+PrintGCDetails，观察耗时和 visualvm 中的内存抽样
 *
 * @author zhaojy
 * @date 2020/9/4 5:20 下午
 */
public class StackAllocation {

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000000; i++) {
            alloc();
        }

        long end = System.currentTimeMillis();
        System.out.println("花费的时间为：" + (end - start) + " ms");

        Thread.sleep(1000000);
    }

    private static void alloc() {
        TestUser user = new TestUser();
        // 未发生逃逸
    }

    public void f() {
        // 多线程访问下，这样加锁没有意义，因为用的是实例锁，没有达到加锁目的，性能还低
        Object lock = new Object();
        synchronized (lock) {
            System.out.println("lock");
        }
    }

}

class TestUser {

}