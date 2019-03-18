package com.std.gof23.L019_templete;

/**
 * @author zhaojy
 * @date 2018-03-16
 */
public class CDAccount extends Account {
    @Override
    protected String doCalculateAccountType() {
        return "Certificate of Deposite";
    }

    @Override
    protected double doCalculateInterestRate() {
        return 0.06;
    }
}
