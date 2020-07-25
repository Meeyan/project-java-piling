package com.std.deep_understand_jvm.zzm.ch04;


import java.util.ArrayList;
import java.util.List;

/**
 * Jconsole 测试 OOM
 * <p>
 * -Xms100m -Xmx100m -XX:+UseSerialGC
 *
 * @author zhaojy
 */
public class JConsoleTest {

    static class OOMObject {
        public byte[] placeHolder = new byte[64 * 1024];
    }

    public static void fillHeap(int num) throws InterruptedException {
        List<OOMObject> dataList = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            Thread.sleep(50);
            dataList.add(new OOMObject());
        }

        System.gc();
    }

    public static void main(String[] args) throws InterruptedException {
        fillHeap(1000);
    }
}
