package com.std.video.geyiming.tec4;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * 非阻塞式同步demo<p/>
 * 对对象的修改不再仅仅是关注结果，同时要关注过程<p/>
 * 可以模拟一直消费和生产<p/>
 * 确保只给消费线程充值一次。
 *
 * @author zhaojy
 * @createTime 2017-05-27
 */
public class AtomicStampedRefDemo {

    // 第二个参数是stamped，用于标识过程中的状态
    static AtomicStampedReference<Integer> money = new AtomicStampedReference<>(19, 0);

    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {

            // 生产线程
            new Thread() {
                public void run() {
                    while (true) {
                        final int stamped = money.getStamp();       // 获取标识状态戳
                        Integer tmpMoney = money.getReference();    // 获取当前值，也即期望值
                        if (tmpMoney < 20) {
                            // 如果期望值和对象中保存的值，并且标识状态戳都相同，视为可以更改。充值20元
                            if (money.compareAndSet(tmpMoney, tmpMoney + 20, stamped, stamped + 1)) {
                                System.out.println(Thread.currentThread().getId() + "--余额小于20元，充值成功，当前余额：" + money.getReference() + " 元");
                                break;
                            } else {
                                System.out.println(Thread.currentThread().getId() + "--充值失败");
                            }
                        } else {
                            // System.out.println("--账户余额>=20，不能充值");
                            break;
                        }

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();
        }

        // 消费线程
        new Thread() {
            public void run() {
                while (true) {
                    int stamped = money.getStamp();             // 获取标识状态戳
                    Integer oMoney = money.getReference();      // 获取当前值，也即期望值
                    if (oMoney > 10) {
                        // 如果期望值和对象中保存的值，并且标识状态戳都相同，则消费掉10块钱
                        if (money.compareAndSet(oMoney, oMoney - 10, stamped, stamped + 1)) {
                            System.out.println("--余额大于10块,成功消费10块，当前余额： " + money.getReference());
                        }
                    } else {
                        System.out.println("--没有足够的金额可以花 ");
                    }

                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
}
