package com.std.gof23.L09_decorator;

/**
 * 具体的一个装饰器，可以装饰一个Person对象
 *
 * @author zhaojy
 * @date 2018-03-13
 */
public class Shirt extends ClothingDecorator {
    private Person person;

    public Shirt(Person person) {
        this.person = person;
    }

    @Override
    public String getDescription() {
        return person.getDescription() + " a shirt...";
    }

    @Override
    public double cost() {
        return 300 + person.cost(); //实现了cost()方法，并调用了person的cost()方法，目的是获得所有累加值
    }
}
