package com.std.video.geyiming.tec3;

/**
 * 可见性测试<p>
 * 运行该测试需要将jdk调成server模式。client模式下无法复现。<p>
 * 虚拟机在进入循环体后，只读取一次stop的值，后续循环不再读取stop的值（如果是个函数获取呢？【函数无效】）<p>
 * 说明：<p>
 * 该例是为了说明，产生可见性问题的成因是非常复杂的。
 * <p>
 * 解决方法：<p>
 * 1. 加volatile修饰符
 *
 * @author zhaojy
 * @date 2017-12-18
 */
public class VisibilityTest extends Thread {
    private boolean stop;

    @Override
    public void run() {
        int i = 0;
        while (!getStop()) {
            i++;
        }
        System.out.println(" finish loop:i=" + i);
    }

    public void stopIt() {
        stop = true;
    }

    /**
     * 使用此方法获取stop的值无效
     *
     * @return boolean
     */
    public boolean getStop() {
        return stop;
    }

    public static void main(String[] args) throws InterruptedException {
        VisibilityTest vi = new VisibilityTest();
        vi.start();

        Thread.sleep(1000);

        vi.stopIt();

        Thread.sleep(2000);
        System.out.println("finish main");
        System.out.println(vi.getStop());

    }
}
