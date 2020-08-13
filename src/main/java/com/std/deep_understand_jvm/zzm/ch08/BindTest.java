package com.std.deep_understand_jvm.zzm.ch08;

/**
 * 早期绑定 和 晚期绑定
 *
 * @author zhaojy
 * @date 2020/8/13 5:33 下午
 */
public class BindTest {

}

class Cat extends Animal implements Huntable {

    public Cat() {
        // 调用 super() 就属于早期绑定
        super();
    }

    public Cat(String name) {
        // 表现为 早期绑定
        this();
    }

    @Override
    void eat() {
        // 调用 super.eat() 就属于早期绑定
        super.eat();
        System.out.println("猫吃鱼");
    }

    @Override
    public void hunt() {

    }
}


class Dog extends Animal implements Huntable {

    @Override
    void eat() {
        // 调用 super.eat() 就属于早期绑定
        super.eat();
    }

    @Override
    public void hunt() {
        System.out.println("狗拿耗子，多管闲事!");
    }
}

class Animal {
    void eat() {

    }
}

/**
 * 捕猎接口
 */
interface Huntable {

    /**
     * 捕猎
     */
    void hunt();
}

