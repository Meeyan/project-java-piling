package com.std.servlet.chp3;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 使用ThreadLocal确保线程安全
 * @author zhaojy
 * @createTime 2017-03-31
 */
public class TLDemo {

    private static ThreadLocal<Connection> numberHolder = new ThreadLocal<Connection>() {
        public Connection initialValue() {
            String url = "";
            try {
                return DriverManager.getConnection(url);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }
    };

    public static Connection getConnection() {
        return numberHolder.get();
    }
}
