package com.std.gof23.L018_strategy;

/**
 * @author zhaojy
 * @date 2018-03-15
 */
public class Context {
    private MemberStrategy strategy;

    public Context(MemberStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * 计算价格
     *
     * @param bookPrice
     * @return
     */
    public double dealPrice(double bookPrice) {
        return strategy.calcPrice(bookPrice);
    }
}
