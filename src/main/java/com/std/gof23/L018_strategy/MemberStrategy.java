package com.std.gof23.L018_strategy;

/**
 * 会员打折策略
 *
 * @author zhaojy
 * @create-time 2018-03-15
 */
public interface MemberStrategy {

    /**
     * 计算书本价格
     *
     * @param bookPrice
     * @return
     */
    double calcPrice(double bookPrice);
}
