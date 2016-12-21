package com.std.test;

import com.std.utils.HttpUtils;

/**
 * 多线程测试
 * 启动3个线程，访问非线程安全的服务，然后观察结果
 * 测试结果：3个线程，各内部发送100请求，结果为300，正确。
 * @author:zhaojy
 * @createTime:2016-12-21
 */
public class TestSafeCnt implements Runnable {
    private String threadName;

    public TestSafeCnt(String threadName) {
        this.threadName = threadName;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            String rep = HttpUtils.httpReq("http://localhost:8080/safeCnt", "", 1);
            System.out.println(this.threadName + "-----" + rep);
        }
    }

    public static void main(String[] args) {
        Runnable run_1 = new TestSafeCnt("thread-1");
        Runnable run_2 = new TestSafeCnt("thread-2");
        Runnable run_3 = new TestSafeCnt("thread-3");
        new Thread(run_1).start();
        new Thread(run_2).start();
        new Thread(run_3).start();
    }
}
