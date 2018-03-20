package com.std.gof23.L022_memento.model_2;

/**
 * @author zhaojy
 * @create-time 2018-03-20
 */
public class Client {
    public static void main(String[] args) {
        Originator originator = new Originator();
        CareTaker taker = new CareTaker();

        //
        originator.setState("on");

        // 创建备忘录对象，将发起人对象的状态存储
        taker.saveMementoIF(originator.createMementoIf());

        // 更改发起人状态
        originator.setState("off");

        // 从负责人角色处获取已经备份的对象，恢复
        originator.restoreMemento(taker.getMementoIF());

        System.out.println("----最终状态：" + originator.getState());

    }
}
