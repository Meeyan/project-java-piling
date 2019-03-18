package com.std.gof23.L13_iterator;

/**
 * @author zhaojy
 * @date 2018-03-13
 */
public class Client {
    public static void main(String[] args) {
        ConcreteMyAggregate con = new ConcreteMyAggregate();
        con.addEle("张三--1");
        con.addEle("张三--2");
        con.addEle("张三--3");
        con.addEle("张三--4");

        MyIterator iterator = con.getIterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.getCurrentObj());
            iterator.next();
        }
    }
}
