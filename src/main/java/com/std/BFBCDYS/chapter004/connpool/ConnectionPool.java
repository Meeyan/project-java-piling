package com.std.BFBCDYS.chapter004.connpool;

import java.util.LinkedList;

/**
 * 模拟连接池
 *
 * @author zhaojy
 * @date 2019-06-22
 */
public class ConnectionPool {
    private final LinkedList<MyConnection> pool = new LinkedList<>();

    /**
     * 初始化连接池
     *
     * @param initSize int 初始化大小
     */
    public ConnectionPool(int initSize) {
        if (initSize > 0) {
            for (int i = 0; i < initSize; i++) {
                pool.addLast(ConnectionDriver.getConnection());
            }
        }
    }

    /**
     * 释放链接
     *
     * @param connection 链接
     */
    public void releaseConnection(MyConnection connection) {
        if (null != connection) {
            synchronized (pool) {
                pool.addLast(connection);

                // 唤醒等待线程
                pool.notifyAll();
            }
        }
    }

    /**
     * 获取链接
     *
     * @param mills 等待时长
     * @return Connection
     * @throws InterruptedException 中断异常
     */
    public MyConnection fetchConnection(long mills) throws InterruptedException {
        synchronized (pool) {
            if (mills < 0) {
                while (pool.isEmpty()) {
                    pool.wait();
                }
                return pool.removeFirst();
            } else {
                Thread thread = Thread.currentThread();
                long future = System.currentTimeMillis() + mills;
                long remaining = mills;
                while (pool.isEmpty() && remaining > 0) {
                    pool.wait(remaining);
                    remaining = future - System.currentTimeMillis();
                }

                MyConnection result = null;
                if (!pool.isEmpty()) {
                    result = pool.removeFirst();
                }
                return result;
            }
        }
    }
}
