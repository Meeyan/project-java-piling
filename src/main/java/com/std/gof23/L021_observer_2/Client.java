package com.std.gof23.L021_observer_2;

/**
 * @author zhaojy
 * @date 2018-03-19
 */
public class Client {
    public static void main(String[] args) {

        // 主题对象
        ConcreteSubject subject = new ConcreteSubject();

        // 观察者
        ObserverA osa_1 = new ObserverA();
        ObserverA osa_2 = new ObserverA();
        ObserverA osa_3 = new ObserverA();

        subject.addObserver(osa_1);
        subject.addObserver(osa_2);
        subject.addObserver(osa_3);

        System.out.println("-------------初始值--------------");
        System.out.println(osa_1.getMyState());
        System.out.println(osa_2.getMyState());
        System.out.println(osa_3.getMyState());

        System.out.println("-------------广播后--------------");
        // 改变subject的状态
        subject.setState(38028);

        System.out.println(osa_1.getMyState());
        System.out.println(osa_2.getMyState());
        System.out.println(osa_3.getMyState());

    }
}
