package com.std.gof23.L05_prototype;

import cn.hutool.core.date.DateUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;

/**
 * 序列化和反序列化实现深复制：通过序列化和反序列化可以达到深复制的目的。不用在对象中做额外操作
 *
 * @author zhaojy
 * @date 2018-03-08
 */
public class ClientDeepClone_2 {
    public static void main(String[] args) throws Exception {
        Date date = DateUtil.date();
        Sheep s1 = new Sheep("少理", date);
        System.out.println(s1);
        System.out.println(s1.getName());
        System.out.println("s1原来的时间:" + s1.getDate());

        // 准备序列化和反序列化
        // 序列化
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos); // 也可以使用文件流
        oos.writeObject(s1);
        byte[] bytes = bos.toByteArray();

        // 修改时间
        long time = date.getTime();
        date.setTime(time + 238832348);
        System.out.println("s1修改后的的时间:" + s1.getDate());

        // 反序列化
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStream(bis);
        Sheep s2 = (Sheep) ois.readObject();

        s2.setName("多里");
        System.out.println(s2);
        System.out.println(s2.getName());
        System.out.println("序列化后的时间:" + s2.getDate());

    }
}
