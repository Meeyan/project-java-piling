package com.std.srljjxnj.zzm.ch03;

/**
 * 3.6.2 测试大对象直接在老年代分配。不推荐，老年代回收比较慢（标记算法）
 * <p>
 * vm 参数：-verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:PretenureSizeThreshold=3145728 -XX:+UseParNewGC
 *
 * @author zhaojy
 * @date 2020/7/21 5:45 PM
 */
public class AllocateBigObjOld {

    private static final int SIZE_1MB = 1024 * 1024;

    public static void main(String[] args) {

        byte[] allocation;
        allocation = new byte[4 * SIZE_1MB];
    }
}
