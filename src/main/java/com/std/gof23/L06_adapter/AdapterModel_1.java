package com.std.gof23.L06_adapter;

/**
 * usb-ps2接口是匹配器
 * 模式1：继承模式
 *
 * @author zhaojy
 * @date 2018-03-08
 */
public class AdapterModel_1 extends UsbAdapter implements PS2Port {
    @Override
    public void usePs2Port() {
        System.out.println("开始使用ps2接口");
        useUsbPort();
    }
}
