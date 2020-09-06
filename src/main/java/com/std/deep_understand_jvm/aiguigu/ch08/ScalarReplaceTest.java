package com.std.deep_understand_jvm.aiguigu.ch08;

/**
 * 标量替换 测试
 * 演示流程：
 *  1. 添加 jvm 参数：-server -Xmx100m -Xms100m -XX:+DoEscapeAnalysis -XX:+PrintGC -XX:-EliminateAllocations 观察耗时和 GC 情况
 *  2. 添加 jvm 参数：-server -Xmx100m -Xms100m -XX:+DoEscapeAnalysis -XX:+PrintGC -XX:+EliminateAllocations 观察耗时和 GC 情况
 *
 * @author zhaojy
 * @date 2020-09-05 12:34
 */
public class ScalarReplaceTest {
    public static class User {
        public int id;
        public String name;
    }

    public static void alloc() {
        // 未发生逃逸
        User u = new User();
        u.id = 5;
        u.name = "lisi爱王五";
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000000; i++) {
            alloc();
        }

        long end = System.currentTimeMillis();
        System.out.println("花费时间为：" + (end - start) + " ms");
    }
}
