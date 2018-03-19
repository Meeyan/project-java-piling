package com.std.gof23.L021_observer;

import java.util.ArrayList;
import java.util.List;

/**
 * 抽象主题角色
 *
 * @author zhaojy
 * @create-time 2018-03-16
 */
public abstract class Subject {

    // 用来保存注册的观察者对象
    private List<Observer> list = new ArrayList<>();

    /**
     * 注册观察者
     *
     * @param observer 观察者
     */
    public void attach(Observer observer) {
        list.add(observer);
        System.out.println("Attached an observer");
    }

    /**
     * 删除观察者对象
     *
     * @param observer 观察者对象
     */
    public void remove(Observer observer) {
        list.remove(observer);
    }

    public void notifyObservers(String newState) {
        for (Observer observer : list) {
            observer.update(newState);
        }
    }


}
