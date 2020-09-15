package com.std.deep_understand_jvm.aiguigu.ch09;


import jdk.internal.org.objectweb.asm.ClassWriter;
import net.bytebuddy.jar.asm.Opcodes;

/**
 * jdk 6 / 7 中：
 * -XX:PermSize=10m -XX:MaxPermSize=10m
 * <p>
 * jdk 8 中：
 * -XX:MetaspaceSize=10m -XX:MaxMetaspaceSize=10m
 *
 * @author zhaojy
 * @date 2020/9/14 6:08 下午
 */
public class OOMTest extends ClassLoader {
    public static void main(String[] args) {
        int j = 0;
        OOMTest oomTest = new OOMTest();
        try {
            for (int i = 0; i < 10000; i++) {

                // 创建 ClassWriter 对象，用于省城类的二进制字节码
                ClassWriter classWriter = new ClassWriter(0);

                // 指明版本号、修饰符、类名、包名，我用的是1.8
                classWriter.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC, "Class" + i, null, "java/lang/Object", null);

                // 获取类的 bytes 数据
                byte[] bytes = classWriter.toByteArray();

                // 类加载
                oomTest.defineClass("Class" + i, bytes, 0, bytes.length);
                j++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println(j);
        }
    }
}
