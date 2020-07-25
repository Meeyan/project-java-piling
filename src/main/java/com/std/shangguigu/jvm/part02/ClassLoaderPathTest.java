package com.std.shangguigu.jvm.part02;

import sun.misc.Launcher;
import sun.security.ec.CurveDB;

import java.net.URL;

/**
 * @author zhaojy
 * @date 2020/7/25 18:35
 */
public class ClassLoaderPathTest {
    public static void main(String[] args) {
        System.out.println("--------------启动类加载器--------------");
        URL[] urLs = Launcher.getBootstrapClassPath().getURLs();
        for (URL element : urLs) {
            System.out.println(element.toExternalForm());
        }

        System.out.println("--------------扩展类加载器--------------");
        String property = System.getProperty("java.ext.dirs");
        for (String path : property.split(";")) {
            System.out.println(path);
        }

        // 验证扩展类加载器
        System.out.println("CurveDB 的类加载器: " + CurveDB.class.getClassLoader());
    }
}
