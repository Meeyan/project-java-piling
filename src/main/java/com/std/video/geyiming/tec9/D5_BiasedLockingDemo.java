package com.std.video.geyiming.tec9;

import java.util.List;
import java.util.Vector;

/**
 * 偏向锁演示
 *  测试时，要先开启偏向锁模式： -XX:+UseBiasedLocking -XX:BiasedLockingStartupDelay=0
 *          关闭偏向锁：-XX:-UseBiasedLocking
 * @author zhaojy
 * @create-time 2018-01-10
 */
public class D5_BiasedLockingDemo {
    static List<Integer> numberList = new Vector<>();

    public static void main(String[] args) {
        long begin = System.currentTimeMillis();
        int count = 0;
        int startNum = 0;
        while (count < 1000000) {
            numberList.add(startNum);
            startNum += 2;
            count++;
        }
        long end = System.currentTimeMillis();
        System.out.println(end - begin);
    }
}
