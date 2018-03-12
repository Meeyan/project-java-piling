package com.std.gof23.L05_prototype;


import cn.hutool.core.date.DateUtil;

import java.io.Serializable;
import java.util.Date;

/**
 * 原型模式
 *
 * @author zhaojy
 * @create-time 2018-03-07
 */
public class Sheep implements Cloneable, Serializable {


    private String name;
    private Date date;

    public Sheep(String name, Date date) {
        this.name = name;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Sheep clone = (Sheep) super.clone();
        clone.date = (Date) this.date.clone();  // 引用对象加此操作，深克隆，不加，就是浅克隆
        return clone;
    }

    public static void main(String[] args) throws CloneNotSupportedException {
        Date date = DateUtil.date();
        Sheep s1 = new Sheep("少理", date);
        Sheep s2 = (Sheep) s1.clone();
        System.out.println(s1);
        System.out.println(s1.getName());
        System.out.println(s1.getDate());

        long time = date.getTime();
        date.setTime(time + 238832348);
        System.out.println("修改后的s1对象的时间:" + s1.getDate());

        s2.setName("多里");
        System.out.println(s2);
        System.out.println(s2.getName());
        System.out.println("s2对象的时间：" + s2.getDate());

    }

}
