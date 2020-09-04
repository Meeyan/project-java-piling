package com.std.deep_understand_jvm.aiguigu.ch08;

/**
 * 测试大对象直接分配到老年代
 * -Xms60m -Xmx60m -XX:NewRatio=2 -XX:SurvivorRatio=8 -XX:+PrintGCDetails
 *
 * @author zhaojy
 * @date 2020/9/3 4:10 下午
 */
public class YoungOldAreaTest {

    public static void main(String[] args) {

        // 20M 的对象
        byte[] buffer = new byte[1024 * 1024 * 20];
    }
}
