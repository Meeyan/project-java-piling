package com.std.test;

import com.std.utils.HttpUtils;

/**
 * 多线程测试
 * 启动3个线程，访问非线程安全的服务，然后观察结果
 * 测试结果：线程安全状态下，3个线程各运行100次，那么输出结果理应为300，但实测不对
 * @author:zhaojy
 * @date:2016-12-21
 */
public class TestUnSafeCnt implements Runnable {
    private String threadName;

    public TestUnSafeCnt(String threadName) {
        this.threadName = threadName;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            String rep = HttpUtils.httpReq("http://localhost:8080/unSafeCnt", "", 1);
            System.out.println(this.threadName + "-----" + rep);
        }
    }

    public static void main(String[] args) {
        Runnable run_1 = new TestUnSafeCnt("thread-1");
        Runnable run_2 = new TestUnSafeCnt("thread-2");
        Runnable run_3 = new TestUnSafeCnt("thread-3");
        new Thread(run_1).start();
        new Thread(run_2).start();
        new Thread(run_3).start();
    }
}
