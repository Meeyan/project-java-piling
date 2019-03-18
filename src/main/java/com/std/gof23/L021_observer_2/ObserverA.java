package com.std.gof23.L021_observer_2;

import java.util.Observable;
import java.util.Observer;

/**
 * 观察者
 *
 * @author zhaojy
 * @date 2018-03-19
 */
public class ObserverA implements Observer {
    private int myState;

    /**
     * 为什么不用参数来实现？
     * 1. 可以使用Observable的子类来获取数据。
     * 2. arg为传入的数据，可以用来操作
     *
     * @param o   Observable
     * @param arg Object
     */
    @Override
    public void update(Observable o, Object arg) {
        myState = ((ConcreteSubject) o).getState();
    }

    public int getMyState() {
        return myState;
    }
}
