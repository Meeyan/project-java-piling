package com.std.gof23.L07_proxy.staticProxy;

/**
 * @author zhaojy
 * @date 2018-03-08
 */
public class StarProxyPerson implements Star {
    Star star;

    public StarProxyPerson(Star star) {
        this.star = star;
    }

    @Override
    public void chatFace() {
        System.out.println("和代理人面谈");
    }

    @Override
    public void signContract() {
        System.out.println("和代理人签约");
    }

    @Override
    public void buyTicket() {
        System.out.println("从代理人哪儿买票");
    }

    /**
     * 调用真实的明星唱歌
     */
    @Override
    public void sing() {
        star.sing();
    }

    @Override
    public void getMoney() {
        System.out.println("把钱给代理人");
    }
}
