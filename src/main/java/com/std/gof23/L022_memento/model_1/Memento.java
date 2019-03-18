package com.std.gof23.L022_memento.model_1;

/**
 * 备忘录角色：备忘录对象将发起人对象传入的状态存储起来
 * @author zhaojy
 * @date 2018-03-20
 */
public class Memento {
    private String state;

    public Memento(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
