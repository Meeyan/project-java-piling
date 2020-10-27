package com.demo.advice_150.exm_004;

import java.text.NumberFormat;

public class Client {

    /**
     * 简单折扣
     *
     * @param price
     * @param discount
     */
    public void calPrice(int price, int discount) {
        float knockDownPrice = price * discount / 100.0F;
        System.out.println("简单折扣后的价格是:" + formatCurrency(knockDownPrice));
    }

    /**
     * 复杂折扣
     *
     * @param price
     * @param discounts
     */
    public void calPrice(int price, int... discounts) {
        float knockDownPrice = price;
        for (int discount : discounts) {
            knockDownPrice = knockDownPrice * discount / 100;
        }
        System.out.println("负责折扣后的价格是：" + formatCurrency(knockDownPrice));
    }

    private String formatCurrency(float price) {
        return NumberFormat.getCurrencyInstance().format(price / 100);
    }

    public static void main(String[] args) {
        Client client = new Client();

        // 为什么运行的是简单折扣？
        client.calPrice(47998, 75);
    }

}
