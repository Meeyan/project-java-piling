package com.std.video.geyiming.tec2;

/**
 * join和yield
 * 解释：<p>
 * 1.在主线程没有使用join时，主线程不会等待其内部开启的线程。从打印出来的信息可以看到。<p>
 * 2.使用join后，主线程会等待子线程（此处为addThread）完成后，再继续运行。此时程序成为串行执行。<p>
 *
 * @author zhaojy
 * @createTime 2017-04-06
 */
public class JoinMain {
    public volatile static int i = 0;

    public static class AddThread extends Thread {
        @Override
        public void run() {
            for (i = 0; i < 1000000; i++) {

            }
            System.out.println("自增线程运行完毕." + i);
        }
    }

    public static void main(String[] args) throws Exception {

        AddThread addThread = new AddThread();
        addThread.start();
        System.out.println("主线程运行完毕");

    }
}
