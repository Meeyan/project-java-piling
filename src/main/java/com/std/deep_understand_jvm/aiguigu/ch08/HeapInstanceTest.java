package com.std.deep_understand_jvm.aiguigu.ch08;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Random;

/**
 * 堆内存分配演示，结合 jvisualvm
 * -Xms600m -Xmx600m
 *
 * @author zhaojy
 * @date 2020/8/21 11:33 上午
 */
public class HeapInstanceTest {

    byte[] buffer = new byte[new Random().nextInt(1024 * 200)];

    public static void main1(String[] args) {
        ArrayList<HeapInstanceTest> instanceTests = new ArrayList<>();
        while (true) {
            instanceTests.add(new HeapInstanceTest());
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        alloc();
    }

    private static void alloc() {
        Point point = new Point(1, 2);
        System.out.println("point.x" + point.x + "; point.y" + point.y);
    }
}

@Data
@AllArgsConstructor
class Point {
    public int x;
    public int y;
}