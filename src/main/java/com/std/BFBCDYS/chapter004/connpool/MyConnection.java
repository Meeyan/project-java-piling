package com.std.BFBCDYS.chapter004.connpool;

/**
 * 自定义链接
 *
 * @author zhaojy
 * @date 2019-06-22 13:20
 */
public interface MyConnection {
    public void commit();

    public void createStatement();
}
