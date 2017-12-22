package com.std.video.geyiming.tec4;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 非阻塞式，针对对象同步的操作
 *
 * @author zhaojy
 * @createTime 2017-05-27
 */
public class AtomicReferencePersonDemo {
    // 初始期望值
    static Person oldPerson = new Person("张三", 18);
    static AtomicReference<Person> atomPerson = new AtomicReference<>(oldPerson);   // 设定初始值

    public static void main(String[] args) throws InterruptedException {

        // 测试字符串
        for (int i = 0; i < 10; i++) {
            final int m = i;
            new Thread() {
                @Override
                public void run() {
                    int i = 0;
                    while (true) {
                        i++;
                        try {
                            Thread.sleep(Math.abs((int) (Math.random() * 100)));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Person person = atomPerson.get();
                        Person newPer = new Person(person.getName() + "0", person.getAge() + 1);
                        if (atomPerson.compareAndSet(oldPerson, newPer)) {
                            System.out.println(atomPerson.get().getName());
                            System.out.println("times: " + i + " , " + Thread.currentThread().getId() + " changed value succeed ");
                            break;
                        } else {
                            System.out.println(Thread.currentThread().getId() + " changed value failed ");
                        }
                    }
                }
            }.start();
        }

        System.out.println(atomPerson.get()); // 更改后，返回的是一个新的引用
    }
}
