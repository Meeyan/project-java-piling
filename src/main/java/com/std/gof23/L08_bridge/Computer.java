package com.std.gof23.L08_bridge;

/**
 * @author zhaojy
 * @create-time 2018-03-09
 */
public class Computer {
    private Brand brand;

    public Computer(Brand brand) {
        this.brand = brand;
    }

    void sale() {
        System.out.println("开始卖：" + brand.getBrandName() + " 牌产品。");
    }
}

class Desktop extends Computer {
    public Desktop(Brand brand) {
        super(brand);
    }

    @Override
    void sale() {
        super.sale();
        System.out.println("产品类型：台式机");
    }
}

class Laptop extends Computer {

    public Laptop(Brand brand) {
        super(brand);
    }

    @Override
    void sale() {
        super.sale();
        System.out.println("产品类型：笔记本");
    }
}

class Pad extends Computer {

    public Pad(Brand brand) {
        super(brand);
    }

    @Override
    void sale() {
        super.sale();
        System.out.println("产品类型：Pad");
    }
}