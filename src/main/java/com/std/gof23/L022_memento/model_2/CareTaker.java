package com.std.gof23.L022_memento.model_2;

/**
 * @author zhaojy
 * @create-time 2018-03-20
 */
public class CareTaker {
    private MementoIF mementoIF;

    /**
     * 备忘录取值方法
     */
    public MementoIF getMementoIF() {
        return mementoIF;
    }

    /**
     * 备忘录设值方法
     */
    public void saveMementoIF(MementoIF mementoIF) {
        this.mementoIF = mementoIF;
    }
}
