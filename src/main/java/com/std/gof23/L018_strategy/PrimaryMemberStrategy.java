package com.std.gof23.L018_strategy;

/**
 * 初级会员的打折策略
 *
 * @author zhaojy
 * @date 2018-03-15
 */
public class PrimaryMemberStrategy implements MemberStrategy {
    @Override
    public double calcPrice(double bookPrice) {
        System.out.println("对初级会员没有折扣");
        return bookPrice;
    }
}
