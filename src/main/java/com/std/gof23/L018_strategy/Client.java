package com.std.gof23.L018_strategy;

/**
 * @author zhaojy
 * @date 2018-03-15
 */
public class Client {

    public static void main(String[] args) {
        MemberStrategy strategy = new AdvancedMemberStrategy();
        Context context = new Context(strategy);
        double v = context.dealPrice(889);
        System.out.println(v);
    }
}
