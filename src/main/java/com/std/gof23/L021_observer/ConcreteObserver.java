package com.std.gof23.L021_observer;

/**
 * @author zhaojy
 * @create-time 2018-03-16
 */
public class ConcreteObserver implements Observer {
    private String observerState;

    @Override
    public void update(String state) {
        observerState = state;
        System.out.println("状态为：" + observerState);
    }
}
