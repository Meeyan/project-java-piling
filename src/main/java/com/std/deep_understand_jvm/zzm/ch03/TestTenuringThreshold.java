package com.std.deep_understand_jvm.zzm.ch03;

/**
 * 测试 3.6.3 ：长期存活对象将进入老年代【对 ParNew 有效，对PS收集器无效】
 * <p>
 * vm 参数：-verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=1 -XX:+PrintTenuringDistribution -XX:+UseParNewGC
 *
 * @author zhaojy
 * @date 2020/7/21 6:13 PM
 */
public class TestTenuringThreshold {
    private static final int SIZE_1MB = 1024 * 1024;

    public static void main(String[] args) {

        byte[] allocation1, allocation2, allocation3, allocation4;
        allocation1 = new byte[SIZE_1MB / 4];
        allocation2 = new byte[4 * SIZE_1MB];
        allocation3 = new byte[4 * SIZE_1MB];
        allocation3 = null;
        allocation3 = new byte[4 * SIZE_1MB];

    }
}
