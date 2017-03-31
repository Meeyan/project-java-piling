package com.std.servlet.chp3;

/**
 * 测试发布状态
 */
public class UnsafeStates {
    private String[] states = new String[]{"AL", "AK", "AM", "EU"};
    private String[] statesIn;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getStates() {
        return states;
    }

    public String[] getStatesIn() {
        return statesIn;
    }

    public void setStatesIn(String[] statesIn) {
        this.statesIn = statesIn;
    }

    public static void main(String[] args) {
        UnsafeStates unsafeStates = new UnsafeStates();
        String[] states = unsafeStates.getStates();
        unsafeStates.setName("李四");
        unsafeStates.setStatesIn(new String[]{"lisi", "wangsu", "zhangsna"});

        String name = unsafeStates.getName();
        name = "哈哈哈";

        String[] statesIn = unsafeStates.getStatesIn();
        statesIn = new String[]{"fuckyou", "tg"};

        states = new String[]{"1", "2"};
        System.out.println(unsafeStates.getStates());
        System.out.println(unsafeStates.getName());
    }
}
