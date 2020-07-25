package com.std.deeperUnderstandJVM.zzm.ch03;

/**
 * 3.8.4 动态对象年龄判断
 * <p>
 * vm 参数：-verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
 *
 * @author zhaojy
 * @date 2020/7/23 4:27 PM
 */
public class DynamicCheckSurvivor {

    private static final int SIZE_1MB = 1024 * 1024;

    public static void main(String[] args) {
        byte[] allocation1, allocation2, allocation3, allocation4;
        allocation1 = new byte[SIZE_1MB / 4];
        allocation2 = new byte[SIZE_1MB / 4];

        allocation3 = new byte[4 * SIZE_1MB];

        allocation4 = new byte[4 * SIZE_1MB];

        allocation4 = null;

        allocation4 = new byte[4 * SIZE_1MB];
    }
}
