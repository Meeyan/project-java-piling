package com.std.gof23.L09_decorator;

/**
 * @author zhaojy
 * @date 2018-03-13
 */
public abstract class Person {
    String description = "unknown";

    public String getDescription() {
        return description;
    }

    // 花费
    public abstract double cost();

}


/**
 * 被装饰者
 */
class Teenager extends Person {

    public Teenager() {
        description = "Description info:";
    }

    @Override
    public double cost() {
        //什么都没买，不用钱
        return 0;
    }
}

