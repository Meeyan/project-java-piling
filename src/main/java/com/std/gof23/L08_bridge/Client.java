package com.std.gof23.L08_bridge;

/**
 * @author zhaojy
 * @create-time 2018-03-09
 */
public class Client {
    public static void main(String[] args) {
        Computer cl = new Desktop(new Lenevo());
        cl.sale();

        Computer cd = new Laptop(new Dell());
        cd.sale();
    }
}
