package com.std.gof23.L06_adapter;

/**
 * 对象适配模式：通过组合来实现适配器功能
 *
 * @author zhaojy
 * @create-time 2018-03-08
 */
public class AdapterModel_2 implements PS2Port {
    UsbPort usbPort;

    public AdapterModel_2(UsbPort usbPort) {
        this.usbPort = usbPort;
    }

    @Override
    public void usePs2Port() {
        System.out.println("开始使用ps2接口");
        usbPort.useUsbPort();
    }
}
