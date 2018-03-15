package com.std.gof23.L019_templete;

/**
 * @author zhaojy
 * @create-time 2018-03-15
 */
public abstract class Account {

    /**
     * 模板方法，计算利息金额
     *
     * @return
     */
    public final double calculateInterest() {
        double interestRate = doCalculateInterestRate();
        String accountType = doCalculateAccountType();
        double amount = calculateAmount(accountType);
        return amount * interestRate;
    }

    /**
     * 基本方法留给子类实现【抽象方法】
     *
     * @return
     */
    protected abstract String doCalculateAccountType();

    /**
     * 基本方法留给子类实现【抽象方法】
     *
     * @return
     */
    protected abstract double doCalculateInterestRate();

    /**
     * 基本方法，已经实现
     *
     * @param accountType
     * @return
     */
    private double calculateAmount(String accountType) {
        return 5000.0;
    }

    /**
     * 钩子方法，子类可以加以扩展
     */
    protected void printInfo() {
    }
}
