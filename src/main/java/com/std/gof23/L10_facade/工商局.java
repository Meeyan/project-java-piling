package com.std.gof23.L10_facade;

/**
 * @author zhaojy
 * @date 2018-03-13
 */
public interface 工商局 {
    void checkName();
}

class 海淀区工商局 implements 工商局 {

    @Override
    public void checkName() {
        System.out.println("海淀区工商局开始---检查");
    }
}
