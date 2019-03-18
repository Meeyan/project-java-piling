package com.std.gof23.L018_strategy;

/**
 * 中级会员打折策略
 *
 * @author zhaojy
 * @date 2018-03-15
 */
public class IntermediateMemberStrategy implements MemberStrategy {
    @Override
    public double calcPrice(double bookPrice) {
        System.out.println("中级会员打9折");
        return bookPrice * 0.9;
    }
}
