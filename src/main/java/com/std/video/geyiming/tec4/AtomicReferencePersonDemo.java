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

                    try {
                        Thread.sleep(Math.abs((int) (Math.random() * 100)));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Person person = atomPerson.get();
                    Person newPer = new Person(person.getName() + "0", person.getAge() + 1);
                    /*
                     * 两种写法：
                     *  1. if (atomPerson.compareAndSet(oldPerson, newPer)) : 保证所有的线程只有一个线程可以修改成功，
                     *    原因：atomPerson本身所hold的对象已经被修改为newPer，其他线程再用oldPerson来和atomPerson本身hold的对象做对比，显然false
                     *  2. if (atomPerson.compareAndSet(person, newPer)) : 所有的线程都可以修改，因为每一次获取的对象都是在新修改的基础上做的。
                     */
                    if (atomPerson.compareAndSet(person, newPer)) {
                        System.out.println(atomPerson.get().getName());
                        System.out.println(Thread.currentThread().getId() + " changed value succeed ");
                    } else {
                        System.out.println(Thread.currentThread().getId() + " changed value failed ");
                    }
                }
            }.start();
        }

        System.out.println(atomPerson.get()); // 更改后，返回的是一个新的引用
    }
}
