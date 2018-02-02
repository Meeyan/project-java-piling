package com.std.video.geyiming.tec4;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * 只能更新对象内部的int字段么？
 *
 * @author zhaojy
 * @createTime 2017-06-02
 */
public class D7_AtomicIntegerFieldUpdaterDemo {

    public static class Candidate {
        int id;
        volatile int score;
    }

    // 创建一个更新器，内部使用反射获取字段
    private final static AtomicIntegerFieldUpdater fieldUpdater = AtomicIntegerFieldUpdater.newUpdater(Candidate.class, "score");

    public static final AtomicInteger checkObj = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        final Candidate candidate = new Candidate();

        List<Thread> list = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            list.add(new Thread() {
                @Override
                public void run() {
                    if (Math.random() > 0.4) {  // 各种概率下的测试
                        fieldUpdater.incrementAndGet(candidate);    // 自增一次
                        checkObj.incrementAndGet(); //  为了验证结果
                    }
                }
            });

            list.get(list.size() - 1).start();  // 启动线程
        }

        for (Thread th : list) {
            th.join();
        }

        System.out.println(candidate.score);
        System.out.println(checkObj);

    }

}
