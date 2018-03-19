package com.std.gof23.L019_templete;

/**
 * @author zhaojy
 * @create-time 2018-03-16
 */
public class MoneyMarketAccount extends Account {

    @Override
    protected String doCalculateAccountType() {
        return "Money Market";
    }

    @Override
    protected double doCalculateInterestRate() {
        return 0.045;
    }
}
