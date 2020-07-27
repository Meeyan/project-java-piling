package com.std.shangguigu.jvm.ch02;

import java.io.FileNotFoundException;

/**
 * 自定义类加载器
 *
 * @author zhaojy
 * @date 2020/7/25 18:49
 */
public class CustomClassLoader extends ClassLoader {

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] result = getClassFromCustomPath(name);

        try {
            if (result == null) {
                throw new FileNotFoundException();
            } else {
                return defineClass(name, result, 0, result.length);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        throw new ClassNotFoundException("name class not found");

    }

    /**
     * 获取 指定 name 对应的 class 的流数据
     *
     * @param name String
     * @return byte[]
     */
    public byte[] getClassFromCustomPath(String name) {
        // 从自定义路径中加载指定类，获取到类的二进制流
        // 如果有解密需求的话，可以在此处解密，然后返回数据。
        return null;
    }

    public static void main(String[] args) {
        CustomClassLoader customClassLoader = new CustomClassLoader();
        try {
            Class<?> one = Class.forName("one", true, customClassLoader);
            Object o = one.newInstance();
            System.out.println(o.getClass().getClassLoader());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
