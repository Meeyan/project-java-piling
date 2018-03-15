package com.std.gof23.L018_strategy;

/**
 * 高级会员打折策略
 *
 * @author zhaojy
 * @create-time 2018-03-15
 */
public class AdvancedMemberStrategy implements MemberStrategy {
    @Override
    public double calcPrice(double bookPrice) {
        System.out.println("高级会员打8折");
        return bookPrice * 0.8;
    }
}
