package com.std.gof23.L021_observer;

/**
 * @author zhaojy
 * @create-time 2018-03-16
 */
public class Client {
    public static void main(String[] args) {
        // 创建主题对象
        ConcreteSubject subject = new ConcreteSubject();

        // 具体观察者对象
        Observer observer = new ConcreteObserver();

        subject.attach(observer);   // 观察者登记

        subject.change("new state");


        subject.change("new state - 1");
    }
}
