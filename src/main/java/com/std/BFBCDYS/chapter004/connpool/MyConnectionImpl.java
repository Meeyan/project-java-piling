package com.std.BFBCDYS.chapter004.connpool;

import java.util.concurrent.TimeUnit;

/**
 * @author zhaojy
 * @date 2019-06-22 13:30
 */
public class MyConnectionImpl implements MyConnection {
    @Override
    public void commit() {
        try {
            TimeUnit.MILLISECONDS.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("要提交了");
    }

    @Override
    public void createStatement() {
        System.out.println("要创建语句了");
    }
}
