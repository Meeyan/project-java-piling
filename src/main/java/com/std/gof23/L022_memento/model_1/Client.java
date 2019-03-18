package com.std.gof23.L022_memento.model_1;

/**
 * @author zhaojy
 * @date 2018-03-20
 */
public class Client {
    public static void main(String[] args) {
        Originator originator = new Originator();
        CareTaker careTaker = new CareTaker();

        // 改变发起人的状态
        originator.setState("89");

        // 创建备忘录对象，将发起人对象状态存储起来
        careTaker.saveMemento(originator.createMemento());

        // 修改发起人状态
        originator.setState("off");

        // 恢复状态
        originator.restoreMemento(careTaker.retrieveMemento());

        System.out.println(originator.getState());

    }
}
