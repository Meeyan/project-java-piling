package com.std.nio_ifeve;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * nio.path 演示
 * @author zhaojy
 * @date 2018-01-10
 */
public class NioPathDemo_1 {
    public static void main(String[] args) {
        String pathStr = "D:\\basic_dev\\apache-maven-3.5.0\\..";
        Path path = Paths.get(pathStr);
        // 路径中带有..,不会消除
        System.out.println(path);

        // 消除路径中的点，解析为明显的路径
        System.out.println(path.normalize());
    }
}
