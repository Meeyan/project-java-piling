package com.demo.collection;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author zhaojy
 * @date 2020/11/19 5:21 下午
 */
public class DelayQueueExample {
    public static void main(String[] args) throws InterruptedException {
        DelayQueue<DelayElement> queue = new DelayQueue<>();
        DelayElement element = new DelayElement();
        queue.put(element);
        DelayElement take = queue.take();
        System.out.println(take);
    }

}

class DelayElement implements Delayed {

    @Override
    public long getDelay(TimeUnit unit) {
        return 10L;
    }

    @Override
    public int compareTo(Delayed o) {
        return 0;
    }
}
