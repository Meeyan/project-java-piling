package com.std.gof23.L022_memento.model_3;

import java.util.ArrayList;
import java.util.List;

/**
 * 备忘录角色：每一个备忘录角色都包含很多状态（点）
 *
 * @author zhaojy
 * @date 2018-03-20
 */
public class Memento {

    private List<String> states;
    private int index;

    public Memento(List<String> states, int index) {
        this.states = new ArrayList<>(states);
        this.index = index;
    }

    public List<String> getStates() {
        return states;
    }

    public int getIndex() {
        return index;
    }
}
