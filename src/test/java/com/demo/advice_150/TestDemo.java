package com.demo.advice_150;

import com.common.DefaultThreadFactory;
import com.util.BinaryUtil;
import org.junit.Test;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TestDemo {

    /**
     * 核心数量
     */
    static private int coreProcessors = Runtime.getRuntime().availableProcessors();

    @Test
    public void sayHello() {

    }

    public static void main(String[] args) {
        System.out.println(1 << 30);
        System.out.println(Integer.MAX_VALUE);
        testState();
    }

    public void testUser() {
        System.out.println(~2);
    }

    public static void testState() {
        System.out.println((3 << (Integer.SIZE - 3)));
        System.out.println(BinaryUtil.formatBinaryStr32(Integer.toBinaryString((3 << (Integer.SIZE - 3)))));
    }

    public static void testPool() {
        LinkedBlockingDeque<Runnable> blockingDeque = new LinkedBlockingDeque<>();


        ThreadFactory threadFactory = new DefaultThreadFactory("test");

        // 创建线程池
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                coreProcessors,
                coreProcessors * 3,
                3000L,
                TimeUnit.MILLISECONDS,
                blockingDeque,
                threadFactory);
    }

    @Test
    public void testStock() {
        double oriMoney = 10000.00;
        int times = 0;
        double destMoney = 100000.00;
        double profitRate = 0.05;
        double tmpMoney = oriMoney;
        while (tmpMoney < destMoney) {
            tmpMoney = tmpMoney + tmpMoney * profitRate;
            times++;
        }
        System.out.printf("本金：%s, 实现 %s 收益，每天收益率：%s，需要的总上涨次数：%s", oriMoney, destMoney, profitRate, times);
    }

}

