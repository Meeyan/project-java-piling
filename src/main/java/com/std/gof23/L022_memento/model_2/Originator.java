package com.std.gof23.L022_memento.model_2;

/**
 * @author zhaojy
 * @create-time 2018-03-20
 */
public class Originator {

    private String state;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
        System.out.println("赋值状态:" + state);
    }

    /**
     * 创建备忘录对象
     *
     * @return
     */
    public MementoIF createMementoIf() {
        return new Memento(state);
    }

    /**
     * 恢复发起人的状态
     *
     * @param mementoIF
     */
    public void restoreMemento(MementoIF mementoIF) {
        this.setState(((Memento) mementoIF).getState());
    }

    /**
     * 备忘录对象被设定到发起对象内部，对外仅暴露一个接口
     *
     * @author zhaojy
     */
    private class Memento implements MementoIF {
        private String state;

        private Memento(String state) {
            this.state = state;
        }

        private String getState() {
            return this.state;
        }

        private void setState(String state) {
            this.state = state;
        }
    }

}
