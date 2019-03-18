package com.std.gof23.L10_facade;

/**
 * @author zhaojy
 * @date 2018-03-13
 */
public interface 税务局 {
    void taxCertificate();
}

class 海淀区税务局 implements 税务局 {

    @Override
    public void taxCertificate() {
        System.out.println("海淀区税务局开始---干活");
    }
}
