package com.std.srljjxnj.zzm.ch03;

/**
 * 3.6.1 测试
 * <p>
 * vm 参数：-verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
 *
 * @author zhaojy
 * @date 2020/7/21 5:18 PM
 */
public class AllocateEden {

    private static final int SIZE_1MB = 1024 * 1024;

    public static void main(String[] args) {

        byte[] allocation1, allocation2, allocation3, allocation4;
        allocation1 = new byte[2 * SIZE_1MB];
        allocation2 = new byte[2 * SIZE_1MB];
        allocation3 = new byte[2 * SIZE_1MB];
        allocation4 = new byte[4 * SIZE_1MB];
    }
}
