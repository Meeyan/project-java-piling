package com.std.gof23.L06_adapter;

/**
 * ps2键盘，如果想用usb的接口，需要一个适配器
 *
 * @author zhaojy
 * @date 2018-03-08
 */
public class PS2KeyBoard {
    AdapterModel_1 adapter;

    /**
     * 打字
     */
    public void type() {
        System.out.println("准备打字..");
        adapter.usePs2Port();
    }

    public PS2KeyBoard(AdapterModel_1 adapter) {
        this.adapter = adapter;
    }

    public static void main(String[] args) {
        AdapterModel_1 adapter = new AdapterModel_1();
        PS2KeyBoard keyBoard = new PS2KeyBoard(adapter);
        keyBoard.type();
    }

}
