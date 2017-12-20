package com.std.video.geyiming.tec2;

/**
 * 线程中断方式2
 *
 * @author zhaojy
 * @createTime 2017-04-01
 */
public class ThreadInterrupt_2 implements Runnable {
    @Override
    public void run() {
        while (true) {
            if (Thread.currentThread().isInterrupted()) {
                System.out.println("当前线程：" + Thread.currentThread().getName() + ",被其他人干掉了");
                break;
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {  // 注意，这个是中断异常，即在休息的过程中被别人干掉了
                System.out.println(" 当前线程正休息呢，被别的线程干掉了。。尼玛 ");

                // 中断线程，设置中断状态，抛出异常后会清除中断标识位
                Thread.currentThread().interrupt();
            }
            Thread.yield();
        }
    }
}
