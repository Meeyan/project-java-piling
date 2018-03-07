package com.std.gof23.L01_singleton;

import java.lang.reflect.Constructor;

/**
 * 通过反射破解单例【我的实验不成功，不能适用懒汉式？】
 *
 * @author zhaojy
 * @create-time 2018-03-05
 */
public class D6_AntiSingleton {
    private static D6_AntiSingleton instance = null;

    private D6_AntiSingleton() {
        // 防止破坏单例模式
        if (instance != null) {
            throw new RuntimeException();
        }
    }

    public static synchronized D6_AntiSingleton getInstance() {
        if (instance == null) {
            instance = new D6_AntiSingleton();
        }
        return instance;
    }

    public static void main(String[] args) throws Exception {
        Class<D6_AntiSingleton> clazz = (Class<D6_AntiSingleton>) Class.forName("com.std.gof23.L01_singleton.D6_AntiSingleton");
        Constructor<D6_AntiSingleton> constructor = clazz.getDeclaredConstructor(null);
        constructor.setAccessible(true);    // 可以访问私有构造
        D6_AntiSingleton inst_1 = constructor.newInstance();
        D6_AntiSingleton inst_2 = constructor.newInstance();

        // 但是生成的实例不是同一个，破坏了单例模式
        System.out.println(inst_1);
        System.out.println(inst_2);
    }
}
