package com.std.gof23.L07_proxy.staticProxy;

/**
 * 静态代理模式
 * 客户想听周几轮唱歌，通过代理人实现
 *
 * @author zhaojy
 * @date 2018-03-08
 */
public class Client {
    public static void main(String[] args) {
        Star star = new ZhouJieLun();
        StarProxyPerson starProxy = new StarProxyPerson(star);
        starProxy.chatFace();
        starProxy.signContract();
        starProxy.buyTicket();
        starProxy.sing();
        starProxy.getMoney();
    }
}
