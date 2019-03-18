package com.std.gof23.L07_proxy.dynamicProxy;

/**
 * 周杰伦是个明星
 *
 * @author zhaojy
 * @date 2018-03-08
 */
public class ZhouJieLun implements Star {
    @Override
    public void chatFace() {
        System.out.println("和周杰伦面谈");
    }

    @Override
    public void signContract() {
        System.out.println("和周杰伦签约");
    }

    @Override
    public void buyTicket() {
        System.out.println("从周杰伦哪儿买票");
    }

    @Override
    public void sing() {
        System.out.println("周杰伦开始唱歌");
        System.out.println("开始唱：菊花残，满地伤，你的笑容已断肠");
    }

    @Override
    public void getMoney() {
        System.out.println("把钱给周杰伦");
    }

    @Override
    public void actMovies() {
        System.out.println("周杰伦开始演电影");
    }
}
