package com.std.gof23.L022_memento.model_1;

/**
 * 负责人角色：负责人角色负责保存备忘录对象，但是从不修改（甚至不查看）备忘录对象的内容
 *
 * @author zhaojy
 * @date 2018-03-20
 */
public class CareTaker {
    private Memento memento;

    /**
     * 备忘录的取值方法
     */
    public Memento retrieveMemento() {
        return this.memento;
    }

    /**
     * 备忘录的赋值方法
     */
    public void saveMemento(Memento memento) {
        this.memento = memento;
    }

}
