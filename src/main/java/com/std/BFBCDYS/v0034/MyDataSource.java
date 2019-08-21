package com.std.BFBCDYS.v0034;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 简单实现线程池
 * 可以使用wait/notifyAll实现
 * 可以使用lock/condition实现
 * <p>
 * 任务：学习Druid的原理
 *
 * @author zhaojy
 * @date 2019/8/21 23:37
 */
public class MyDataSource {
    private final LinkedList<Connection> pool = new LinkedList<>();
    private static final int INIT_CONNECTIONS = 10;
    private static final String DRIVER_CLASS = "com.mysql.jdbc.Driver";
    private static final String USER = "user";
    private static final String PASSWORD = "";
    private static final String URL = "";

    private static final Lock lock = new ReentrantLock();
    private static final Condition GET_CONDITION = lock.newCondition();

    static {
        try {
            Class.forName(DRIVER_CLASS);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public MyDataSource() {
        /**
         * 初始化连池
         */
        for (int i = 0; i < INIT_CONNECTIONS; i++) {
            try {
                Connection connection = DriverManager.getConnection(URL, MyDataSource.USER, PASSWORD);
                pool.add(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Connection getConnection() {
        Connection connection = null;
        lock.lock();
        try {
            while (pool.size() <= 0) {
                try {
                    GET_CONDITION.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if (pool.isEmpty()) {
                connection = pool.getFirst();
            }
        } finally {
            lock.unlock();
        }
        return connection;
    }

    /**
     * 释放连接
     *
     * @param connection Connection
     */
    public void releaseConnection(Connection connection) {
        if (connection != null) {
            try {
                lock.lock();
                pool.add(connection);
                GET_CONDITION.signal();
            } finally {
                lock.unlock();
            }
        }
    }
}
