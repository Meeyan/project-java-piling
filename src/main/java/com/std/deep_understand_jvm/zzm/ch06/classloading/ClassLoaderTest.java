package com.std.deep_understand_jvm.zzm.ch06.classloading;

import org.junit.Test;

import java.io.InputStream;

/**
 * 测试 ClassLoader
 * <p>
 * 加载类时，使用 不同的 类加载器，将会导致，同一个类的不同。
 *
 * @author zhaojy
 * @date 2020/8/4 10:49 上午
 */
public class ClassLoaderTest {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        ClassLoader loader = new ClassLoader() {

            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                try {

                    String fileName = name.substring(name.lastIndexOf(".") + 1) + ".class";
                    InputStream resourceAsStream = getClass().getResourceAsStream(fileName);
                    if (resourceAsStream == null) {
                        return super.loadClass(name);
                    }
                    byte[] bytes = new byte[resourceAsStream.available()];
                    resourceAsStream.read(bytes);
                    return defineClass(name, bytes, 0, bytes.length);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new ClassNotFoundException(name);
                }
            }

        };

        Object instance = loader.loadClass("com.std.deep_understand_jvm.zzm.ch06.classloading.ClassLoaderTest").newInstance();
        System.out.println(instance.getClass());
        System.out.println(instance instanceof com.std.deep_understand_jvm.zzm.ch06.classloading.ClassLoaderTest);
    }

    @Test
    public void getClassLoader() {
        System.out.println(ClassLoaderTest.class.getClassLoader());
    }

}
