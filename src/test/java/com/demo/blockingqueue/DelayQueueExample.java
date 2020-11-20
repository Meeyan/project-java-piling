package com.demo.blockingqueue;

import cn.hutool.core.util.ObjectUtil;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 延迟队列 example
 * <p>
 * 参考 ： https://my.oschina.net/lujianing/blog/705894
 *
 * @author zhaojy
 * @date 2020/11/19 5:21 下午
 */
public class DelayQueueExample {
    public static void main(String[] args) {
        DelayQueue<DelayElement> queue = new DelayQueue<>();
        // 生产者
        producer(queue);

        // 消费者
        consumer(queue);

        while (true) {
            try {
                TimeUnit.HOURS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    static void producer(final DelayQueue<DelayElement> delayQueue) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(100);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    DelayElement element = new DelayElement(1000, "test");
                    delayQueue.offer(element);
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(2000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println("delayed queue count:" + delayQueue.size());
                }
            }
        }).start();
    }

    static void consumer(final DelayQueue<DelayElement> delayQueue) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    DelayElement element = null;
                    try {
                        element = delayQueue.poll();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (ObjectUtil.isNotNull(element)) {
                        System.out.println(System.currentTimeMillis() + "-" + element);
                    }
                }
            }
        }).start();
    }

}

class DelayElement implements Delayed {

    /**
     * 延迟时间
     */
    private final long delay;

    /**
     * 过期时间
     */
    private final long expire;

    private final String msg;
    /**
     * 创建时间
     */
    private final long now;

    public DelayElement(long delay, String msg) {
        this.delay = delay;
        this.msg = msg;
        expire = System.currentTimeMillis() + delay;
        now = System.currentTimeMillis();
    }

    /**
     * 需要实现的接口，获得延迟时间   用过期时间-当前时间
     *
     * @param unit TimeUnit
     * @return long
     */
    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(this.expire - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        return (int) (this.getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS));
    }

    @Override
    public String toString() {
        return "DelayElement{" +
                "delay=" + delay +
                ", expire=" + expire +
                ", msg='" + msg + '\'' +
                ", now=" + now +
                '}';
    }
}
