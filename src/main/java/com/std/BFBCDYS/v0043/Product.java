package com.std.BFBCDYS.v0043;

import lombok.Data;

/**
 * @author zhaojy
 * @date 2019/10/30 8:27 PM
 */
@Data
public class Product {
    private int id;
    private String name;

    public Product(int id, String name) {
        this.id = id;
        this.name = name;

        System.out.println("开始生产" + name);
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("蛋糕生产完毕");

    }

    public Product() {
    }
}
