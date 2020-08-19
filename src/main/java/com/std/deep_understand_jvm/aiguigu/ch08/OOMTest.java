package com.std.deep_understand_jvm.aiguigu.ch08;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 测试 OOM
 *
 * -Xms600m -Xmx600m
 *
 * @author zhaojy
 * @date 2020/8/19 6:33 下午
 */
public class OOMTest {
    public static void main(String[] args) throws InterruptedException {
        List<Picture> list = new ArrayList<>();

        Thread.sleep(20);

        while (true) {
            list.add(new Picture(new Random().nextInt(1024 * 1024)));
        }
    }
}


class Picture {

    private byte[] pixels;

    public Picture(int length) {
        this.pixels = new byte[length];
    }
}
