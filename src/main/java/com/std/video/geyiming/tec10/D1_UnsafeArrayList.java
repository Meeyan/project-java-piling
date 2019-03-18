package com.std.video.geyiming.tec10;

import java.util.ArrayList;

/**
 * 就是为了测试，怎么在多线程环境下打断点。
 *   idea需要将breakpoint模式调成thread模式。<br>
 *   产生bug的原理：在add方中，在List需要扩容前的最后一次add操作时，th1走到了‘elementData[size++] = e;’停了下来，而th2进入add开始跑完，
 *  add完成(size已经被增加)；然后th1继续，就会引发异常。
 * @author zhaojy
 * @date 2018-01-17
 */
public class D1_UnsafeArrayList {

    // 不是线程安全
    static ArrayList al = new ArrayList();

    static class AddTask implements Runnable {

        @Override
        public void run() {
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }

            for (int i = 0; i < 1000000; i++) {
                al.add(new Object());
            }
        }
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(new AddTask(), "t1");
        Thread t2 = new Thread(new AddTask(), "t2");

        t1.start();
        t2.start();

        //

        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                //
                while (true) {
                    try {
                        Thread.sleep(100);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "t3");
        t3.start();
    }

}
