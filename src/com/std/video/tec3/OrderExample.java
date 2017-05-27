package com.std.video.tec3;

/**
 * 有序性示例
 *
 * @author zhaojy
 * @createTime 2017-05-19
 */
public class OrderExample {
    private int a = 0;
    boolean flag = false;

    public void write() {
        flag = true;
        this.a = 1;
    }

    public void read() {
        if (flag) {
            int i = a + 1;
            System.out.println(i);
        }
    }

    /**
     * 讲解：多个线程，一个线程A负责write，另一个线程B负责read。
     * 当A执行到flag=true;时，B开始执行，那么此时B就会误认为a为1，实则不然。
     */
}
