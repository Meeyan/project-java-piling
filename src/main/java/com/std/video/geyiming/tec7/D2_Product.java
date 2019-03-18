package com.std.video.geyiming.tec7;

/**
 * 不变模式对象
 * 类为final确保无子类
 *
 * @author zhaojy
 * @date 2017-12-27
 */
public final class D2_Product {
    // 所有字段都为final，保证不会被2次赋值
    // 同时，不预留set方法。
    private final String no;
    private final String name;
    private final double price;

    /**
     * 对象被创建后，无法被修改
     *
     * @param no    String
     * @param name  String
     * @param price double
     */
    public D2_Product(String no, String name, double price) {
        this.no = no;
        this.name = name;
        this.price = price;
    }

    public String getNo() {
        return no;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public static void main(String[] args) {
        String str = new String("jsdfjaio");
        str = "abdkl";
        System.out.println(str);
    }
}
