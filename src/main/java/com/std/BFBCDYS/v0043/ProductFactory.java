package com.std.BFBCDYS.v0043;

import java.util.Random;

/**
 * @author zhaojy
 * @date 2019/10/30 8:35 PM
 */
public class ProductFactory {

    private static Random random = new Random(10000);

    public static Order createProductOrder(String name) {
        Order order = new Order();
        synchronized (random) {
            new Thread(() -> {
                Product p = new Product(random.nextInt(), name);
                order.setProduct(p);
            }).start();

            while (order.getProduct() == null) {
                try {
                    random.wait(0L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return order;
    }
}
