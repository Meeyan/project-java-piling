package com.std.gof23.L021_observer;

/**
 * 具体主题角色
 *
 * @author zhaojy
 * @create-time 2018-03-16
 */
public class ConcreteSubject extends Subject {
    String state;

    public String getState() {
        return state;
    }

    public void change(String newState) {
        state = newState;
        System.out.println("主题状态为：" + state);
        this.notifyObservers(newState);
    }

}
