package com.std.BFBCDYS.chapter004;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 模拟链接驱动器
 *
 * @author zhaojy
 * @date 2019-06-22
 */
public class ConnectionDriver {
    static class ConnectionHandler implements InvocationHandler {

        private MyConnection myConnection;

        public ConnectionHandler(MyConnection myConnection) {
            this.myConnection = myConnection;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.getName().equals("commit")) {
                method.invoke(myConnection, args);
            }
            return myConnection;
        }
    }

    /**
     * 创建一个commit的代理
     */
    public static MyConnection getConnection() {

        return (MyConnection) Proxy.newProxyInstance(ConnectionDriver.class.getClassLoader(),
                new Class[]{MyConnection.class},
                new ConnectionHandler(new MyConnectionImpl()));
    }

}
