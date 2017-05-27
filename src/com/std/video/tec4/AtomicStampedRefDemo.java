package com.std.video.tec4;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * 非阻塞式同步demo<p/>
 * 对对象的修改不再仅仅是关注结果，同时要关注过程<p/>
 * 可以模拟一直消费和生产
 *
 * @author zhaojy
 * @createTime 2017-05-27
 */
public class AtomicStampedRefDemo {
    // 第二个参数是stamped，用于标识过程中的状态
    static AtomicStampedReference<Integer> money = new AtomicStampedReference<>(19, 0);

    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {

            new Thread() {
                public void run() {
                    while (true) {
                        final int stamped = money.getStamp();
                        Integer tmpMoney = money.getReference();
                        if (tmpMoney < 20) {
                            if (money.compareAndSet(tmpMoney, tmpMoney + 20, stamped, stamped + 1)) {
                                System.out.println(Thread.currentThread().getId() + "--余额小于20元，充值成功，当前余额：" + money.getReference() + " 元");
                            } else {
                                System.out.println(Thread.currentThread().getId() + "--充值失败");
                            }
                        } else {
                            // System.out.println("--账户余额>=20，不能充值");
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

        new Thread() {
            public void run() {
                while (true) {
                    int stamped = money.getStamp();
                    Integer oMoney = money.getReference();
                    if (oMoney > 10) {
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
