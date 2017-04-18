package com.std.thread.chp2;

/**
 * 测试是否会产生死锁
 * 结果：未发现死锁
 */
public class Widget {
    public synchronized void doSomething() {
        System.out.println("print some infomation ...");
    }
}

class LoggingWidget extends Widget {

    public synchronized void doSomething() {
        System.out.println(toString() + ":calling doSomething..");
        super.doSomething();    // 调用父方法
    }

    public static void main(String[] args) {
        LoggingWidget loggingWidget = new LoggingWidget();
        loggingWidget.doSomething();
    }
}
