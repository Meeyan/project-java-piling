package com.std.gof23.L022_memento.model_3;

/**
 * @author zhaojy
 * @date 2018-03-20
 */
public class Client {
    public static void main(String[] args) {
        Originator originator = new Originator();
        CareTaker careTaker = new CareTaker(originator);

        // 第一次，检查点里只有一个状态值，备份一下
        originator.setState("state-0");
        careTaker.createMemento();

        // 第二次，检查点集合里有2个状态值，备份一下
        originator.setState("state-1");
        careTaker.createMemento();

        originator.setState("state-2");
        careTaker.createMemento();

        originator.setState("state-3");
        careTaker.createMemento();

        originator.printStates();
        System.out.println("--------------恢复检查点-------------");
        careTaker.restoreMemento(3);
        originator.printStates();


    }
}
