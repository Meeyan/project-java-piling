package com.std.gof23.L021_observer_2;

import java.util.Observable;

/**
 * 使用jdk内部的分装类来完成观察者模式
 *
 * @author zhaojy
 * @create-time 2018-03-19
 */
public class ConcreteSubject extends Observable {
    private int state;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;

        // 调用jdk内部的实现，通知观察者
        setChanged();
        notifyObservers(state);
    }
}
