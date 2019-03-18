package com.std.gof23.L10_facade;

/**
 * @author zhaojy
 * @date 2018-03-13
 */
public interface 银行 {
    void openAccount();
}

class 工商银行 implements 银行 {

    @Override
    public void openAccount() {
        System.out.println("工商银行开始---开户");
    }
}
