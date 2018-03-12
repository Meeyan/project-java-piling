package com.std.gof23.L06_adapter;

/**
 * ps2键盘，如果想用usb的接口，需要一个适配器
 *
 * @author zhaojy
 * @create-time 2018-03-08
 */
public class PS2KeyBoard_2 {

    AdapterModel_2 adapter;

    public PS2KeyBoard_2(AdapterModel_2 adapter) {
        this.adapter = adapter;
    }

    /**
     * 打字
     */
    public void type() {
        System.out.println("准备打字..");
        adapter.usePs2Port();
    }

    public static void main(String[] args) {
        UsbPort usbPort = new UsbAdapter();
        AdapterModel_2 adapter = new AdapterModel_2(usbPort);
        PS2KeyBoard_2 keyBoard = new PS2KeyBoard_2(adapter);
        keyBoard.type();
    }
}
