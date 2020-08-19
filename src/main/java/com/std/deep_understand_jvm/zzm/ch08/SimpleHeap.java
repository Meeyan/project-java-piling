package com.std.deep_understand_jvm.zzm.ch08;

/**
 * @author zhaojy
 * @date 2020/8/19 2:36 下午
 */
public class SimpleHeap {
    private int id;

    public SimpleHeap(int id) {
        this.id = id;
    }

    public void show() {
        System.out.println("My id is :" + id);
    }

    public static void main(String[] args) {
        SimpleHeap s1 = new SimpleHeap(1);
        SimpleHeap s2 = new SimpleHeap(2);
    }
}
