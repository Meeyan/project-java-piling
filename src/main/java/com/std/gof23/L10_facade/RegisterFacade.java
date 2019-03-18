package com.std.gof23.L10_facade;

/**
 * 外观模式（门面模式），功能类似中间代理商（不是代理模式）
 *
 * @author zhaojy
 * @date 2018-03-13
 */
public class RegisterFacade {
    static void register() {
        System.out.println("XX代理开始为您注册公司，请稍后");
        工商局 a = new 海淀区工商局();
        a.checkName();

        质监局 b = new 海淀区质监局();
        b.orgCodeCertificate();

        税务局 c = new 海淀区税务局();
        c.taxCertificate();

        银行 d = new 工商银行();
        d.openAccount();

        System.out.println("公司注册完成，谢谢惠顾");
    }
}
