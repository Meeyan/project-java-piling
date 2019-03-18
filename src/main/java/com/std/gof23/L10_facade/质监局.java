package com.std.gof23.L10_facade;

/**
 * @author zhaojy
 * @date 2018-03-13
 */
public interface 质监局 {
    void orgCodeCertificate();
}

class 海淀区质监局 implements 质监局 {

    @Override
    public void orgCodeCertificate() {
        System.out.println("海淀区质监局开始---干活");
    }
}
