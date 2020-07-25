package com.std.shangguigu.jvm.part02;

/**
 * @author zhaojy
 * @date 2020/7/25 18:17
 */
public class ClassLoaderTest {
    public static void main(String[] args) {
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        // sun.misc.Launcher$AppClassLoader@18b4aac2
        System.out.println(systemClassLoader);

        ClassLoader extClassLoader = systemClassLoader.getParent();
        // sun.misc.Launcher$ExtClassLoader@5e9f23b4
        System.out.println(extClassLoader);

        ClassLoader parent = extClassLoader.getParent();
        // null
        System.out.println(parent);

        // 用户自定义类，默认使用 系统类加载器进行加载
        // sun.misc.Launcher$AppClassLoader@18b4aac2
        ClassLoader classLoader = ClassLoaderTest.class.getClassLoader();
        System.out.println(classLoader);

        // String 类使用 引导类加载器记载的。 Java 的核心类库，都是使用 引导类加载器记载的
        ClassLoader classLoader1 = String.class.getClassLoader();
        System.out.println(classLoader1);
    }
}
