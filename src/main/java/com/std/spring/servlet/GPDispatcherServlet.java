package com.std.spring.servlet;

import com.std.spring.annotations.GPAutowired;
import com.std.spring.annotations.GPController;
import com.std.spring.annotations.GPRequestMapping;
import com.std.spring.annotations.GPService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;

/**
 * 模拟spring的流程
 *
 * @author zhaojy
 * @create-time 2018-03-28
 */
public class GPDispatcherServlet extends HttpServlet {
    private Properties properties = new Properties();
    private List<String> classNames = new ArrayList<>();
    private Map<String, Object> ioc = new HashMap<>();  // 容器

    private Map<String, Method> handlerMap = new HashMap<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {

        // 1. 加载配置文件
        doLoadConfig(config.getInitParameter("contextConfigLocation"));

        // 2. 扫描相关类
        doScanner(properties.getProperty("scanPackage"));

        // 3. 初始化所有class类示例，将其保存在ioc容器中
        doInstance();

        // 4. 自动化依赖注入
        doAutowired();

        // 5. 初始化handlerMapping
        doHandlerMapping();

        System.out.println("init finished");
    }

    private void doHandlerMapping() {
        if (ioc.isEmpty()) {
            return;
        }
        for (Map.Entry<String, Object> entry : ioc.entrySet()) {
            Class<?> clazz = entry.getValue().getClass();
            if (!clazz.isAnnotationPresent(GPController.class)) {
                continue;
            }

            String baseUrl = "";
            if (clazz.isAnnotationPresent(GPRequestMapping.class)) {
                GPRequestMapping requestMapping = clazz.getAnnotation(GPRequestMapping.class);
                baseUrl = requestMapping.value();
            }

            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                if (!method.isAnnotationPresent(GPRequestMapping.class)) {
                    continue;
                }
                GPRequestMapping methodAnnotation = method.getAnnotation(GPRequestMapping.class);
                String finalUrl = baseUrl + methodAnnotation.value();
                handlerMap.put(finalUrl, method);
                System.out.println("Mapping:" + finalUrl + "...." + method);
            }
        }
    }

    private void doAutowired() {
        if (ioc.isEmpty()) {
            return;
        }
        for (Map.Entry<String, Object> entry : ioc.entrySet()) {
            Field[] declaredFields = entry.getValue().getClass().getDeclaredFields();   // 获取所偶字段
            for (Field field : declaredFields) {
                // 有自动注入的，就赋值
                if (!field.isAnnotationPresent(GPAutowired.class)) {
                    continue;
                }
                GPAutowired annotation = field.getAnnotation(GPAutowired.class);
                String beanName = annotation.value().trim();
                if ("".equals(beanName)) {
                    beanName = field.getType().getName();
                }

                field.setAccessible(Boolean.TRUE);  // 暴力反射，私有的可以使用

                try {
                    field.set(entry.getValue(), ioc.get(beanName));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private void doInstance() {
        if (classNames.isEmpty()) {
            return;
        }
        try {
            for (String className : classNames) {
                Class<?> clazz = Class.forName(className);

                // 要进行实例化
                // bean name 的定义
                // 1. 没有指定，就用类名首字母小写
                // 2. 指定名字，优先使用自己定义的名字
                // 3. 根据类型匹配，利用接口作为key
                // spring本身的规则更复杂，此处不做默契
                if (clazz.isAnnotationPresent(GPController.class)) {
                    String beanName = lowerFirst(clazz.getSimpleName());
                    ioc.put(beanName, clazz.newInstance());
                } else if (clazz.isAnnotationPresent(GPService.class)) {
                    GPService gpService = clazz.getAnnotation(GPService.class);
                    String beanName = gpService.value();
                    if ("".equals(beanName.trim())) {
                        beanName = lowerFirst(clazz.getSimpleName());
                    }

                    // 为什么要put两次？
                    Object newInstance = clazz.newInstance();
                    ioc.put(beanName, newInstance);

                    Class<?>[] interfaces = clazz.getInterfaces();
                    for (Class cls : interfaces) {
                        ioc.put(cls.getName(), newInstance);
                    }
                } else {

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doScanner(String packageName) {
        packageName = packageName.trim();
        URL resource = this.getClass().getClassLoader().getResource("/" + packageName.replaceAll("\\.", "/"));
        File classDir = new File(resource.getFile());
        if (null != classDir.listFiles()) {
            for (File file : classDir.listFiles()) {
                if (file.isDirectory()) {
                    doScanner(packageName + "." + file.getName());
                } else {
                    String className = (packageName + "." + file.getName().replace(".class", ""));
                    classNames.add(className);
                }
            }
        }
    }

    private void doLoadConfig(String location) {
        InputStream resource = this.getClass().getClassLoader().getResourceAsStream(location);
        try {
            properties.load(resource);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (resource != null) {
                try {
                    resource.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 首字母小写
     *
     * @param str
     * @return
     */
    private String lowerFirst(String str) {
        char[] chars = str.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }


}
