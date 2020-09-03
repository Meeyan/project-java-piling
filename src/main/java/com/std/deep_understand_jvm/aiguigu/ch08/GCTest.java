package com.std.deep_understand_jvm.aiguigu.ch08;

import java.util.ArrayList;
import java.util.List;

/**
 * 示例讲解 OOM
 * 测试：MinorGC，MajorGC，FullGC
 * -Xms9m -Xmx9m -XX:+PrintGCDetails
 *
 * @author zhaojy
 * @date 2020/9/3 11:19 上午
 */
public class GCTest {

    public static void main(String[] args) {

        int i = 0;
        try {
            List<String> list = new ArrayList<>();
            String a = "hello,java Jvm";
            while (true) {
                list.add(a);
                a = a + a;
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("遍历次数为：" + i);
        }
    }

}
