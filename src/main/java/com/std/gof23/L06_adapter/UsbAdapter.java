package com.std.gof23.L06_adapter;

/**
 * usb适配器
 *
 * @author zhaojy
 * @date 2018-03-08
 */
public class UsbAdapter implements UsbPort {
    @Override
    public void useUsbPort() {
        System.out.println("开始使用usb接口");
    }
}
