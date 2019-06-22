package com.std.yeziyuan.L030;

/**
 * 生产者和消费者测试
 *
 * @author zhaojy
 * @date 2019-06-22 15:55
 */
public class TestProducerCustomer {
    public static void main(String[] args) {
        Tmall tmall = new Tmall();
        Producer producer = new Producer(tmall);
        Customer customer = new Customer(tmall);

        for (int i = 0; i < 2; i++) {
            new Thread(producer, "------producer-" + i).start();
        }

        for (int i = 0; i < 5; i++) {
            new Thread(customer, "++++++customer-" + i).start();
        }
    }
}
