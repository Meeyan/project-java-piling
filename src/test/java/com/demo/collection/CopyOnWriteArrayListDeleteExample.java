package com.demo.collection;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * CopyOnWriteArrayList 在 先 size 后 remove/get 操作仍旧会有异常
 *
 * @author zhaojy
 * @date 2020/11/5 10:31 上午
 */
public class CopyOnWriteArrayListDeleteExample {
    public static void main(String[] args) {
        CopyOnWriteArrayList<Integer> cowList = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 10; i++) {
            cowList.add(i);
        }

        new Thread(() -> System.out.println(getLast(cowList))).start();
        new Thread(() -> deleteLast(cowList)).start();
        new Thread(() -> System.out.println(getLast(cowList))).start();
        new Thread(() -> deleteLast(cowList)).start();
    }

    // 得到Vector最后一个元素
    public static Object getLast(CopyOnWriteArrayList list) {
        int lastIndex = list.size() - 1;
        System.out.println("---1---");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return list.get(lastIndex);
    }

    // 删除Vector最后一个元素
    public static void deleteLast(CopyOnWriteArrayList list) {
        int lastIndex = list.size() - 1;
        System.out.println("---2---");
        list.remove(lastIndex);
    }
}

