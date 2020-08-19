package com.std.deep_understand_jvm.zzm.ch08;

/**
 * 测试 虚拟机 堆区初始大小
 *
 * @author zhaojy
 * @date 2020/8/19 6:04 下午
 */
public class HeapSpaceInitial {
    public static void main(String[] args) {
        System.out.println("-Xms : " + Runtime.getRuntime().totalMemory() / 1024 / 1024 + "M");
        System.out.println("-Xmx : " + Runtime.getRuntime().maxMemory() / 1024 / 1024 + "M");

        System.out.println("系统内存大小为：" + Runtime.getRuntime().totalMemory() * 64 / 1024 / 1024 / 1024 + "G");
        System.out.println("系统内存大小为：" + Runtime.getRuntime().maxMemory() * 4 / 1024 / 1024 / 1024 + "G");

//        try {
//            Thread.sleep(10000000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}
