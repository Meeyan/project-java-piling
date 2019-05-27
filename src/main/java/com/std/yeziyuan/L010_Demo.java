package com.std.yeziyuan;

import java.util.Arrays;
import java.util.List;

/**
 * lambda表达式实现多线程
 *
 * @author zhaojy
 * @date 2019-05-27
 */
public class L010_Demo {
    public static void main(String[] args) {
        List<Integer> integers = Arrays.asList(18, 28, 29, 50, 100);
        System.out.println(sumList(integers));
    }

    private static int sumList(List<Integer> dataList) {
        // 打印顺序是乱的，可以说明是异步或者多线程
        dataList.parallelStream().forEach(System.out::println);
        // 第二个i相当于临时临时变量，可以对其处理
        System.out.println(dataList.parallelStream().mapToInt(i -> i * 2).sum());
        return dataList.parallelStream().mapToInt(i -> i).sum();
    }
}
