package com.std.gof23.L022_memento.model_1;

/**
 * 发起人角色
 * 发起人角色利用一个新创建的备忘录对象将自己的内部状态存储起来。
 *
 * @author zhaojy
 * @create-time 2018-03-20
 */
public class Originator {
    private String state;

    /**
     * 工厂方法，返回一个新的备忘录对象
     */
    public Memento createMemento() {
        return new Memento(state);
    }

    public void restoreMemento(Memento memento) {
        this.state = memento.getState();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
        System.out.println("当前状态是：" + state);
    }
}
