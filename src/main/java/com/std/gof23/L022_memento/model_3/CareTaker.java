package com.std.gof23.L022_memento.model_3;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaojy
 * @date 2018-03-20
 */
public class CareTaker {
    private Originator originator;
    private List<Memento> mementos = new ArrayList<>(); // 多个检查点
    private int current;

    public CareTaker(Originator originator) {
        this.originator = originator;
        current = 0;
    }

    /**
     * 创建一个新的检查点
     */
    public int createMemento() {
        Memento memento = originator.createMemento();
        mementos.add(memento);
        return current++;
    }

    /**
     * 将发起人恢复到某个检查点
     */
    public void restoreMemento(int index) {
        Memento memento = mementos.get(index);
        originator.restoreMemento(memento);
    }

    /**
     * 将某个检查点删除
     */
    public void removeMemento(int index) {
        mementos.remove(index);
    }

}
