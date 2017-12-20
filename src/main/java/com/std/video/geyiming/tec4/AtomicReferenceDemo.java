package com.std.video.geyiming.tec4;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 非阻塞式，针对对象同步的操作
 *
 * @author zhaojy
 * @createTime 2017-05-27
 */
public class AtomicReferenceDemo {

    // 操作字符换
    static AtomicReference<String> atomicStr = new AtomicReference<>("abc");

    // 老值，也将做期望值
    static Person oldPerson = new Person("张三", 18);
    static AtomicReference<Person> atomPerson = new AtomicReference<>(oldPerson);   // 将老值存进来

    public static void main(String[] args) throws InterruptedException {

        // 测试字符串
        for (int i = 0; i < 0; i++) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(Math.abs((int) (Math.random() * 100)));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (atomicStr.compareAndSet("abc", "def")) {
                        System.out.println(Thread.currentThread().getId() + " changed value succeed ");
                    } else {
                        System.out.println(Thread.currentThread().getId() + " changed value failed ");
                    }
                }
            }.start();
        }

        oldPerson.setName("王五");    // 即便在此处更改了对象的属性，视为更改的是atomPerson内部存储的值

        atomPerson.get().setName("赵六");         // 此时改也不会导致程序failed

        // 测试对象
        System.out.println(oldPerson);          // 没有更改前
        System.out.println(atomPerson.get());   // 没有更改前
        List<Thread> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Thread t = new Thread() {
                public void run() {
                    Person newPerson = new Person("李四", 18);
                    // Person oldPerson = new Person("张三", 18);    // oldPerson放到此处不行，怀疑是：期望值必须传入老的引用才可以
                    if (atomPerson.compareAndSet(oldPerson, newPerson)) {
                        System.out.println(Thread.currentThread().getId() + " changed person value succeed ");
                    } else {
                        System.out.println(Thread.currentThread().getId() + " changed person value failed ");
                    }
                }
            };
            list.add(t);
        }

        for (Thread th : list) {
            th.start();

        }

        for (Thread th : list) {
            th.join();
        }

        System.out.println(atomPerson.get()); // 更改后，返回的是一个新的引用
    }
}
