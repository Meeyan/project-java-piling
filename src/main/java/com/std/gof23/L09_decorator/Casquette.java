package com.std.gof23.L09_decorator;

/**
 * 帽子装饰器，可以装饰一个Person对象
 *
 * @author zhaojy
 * @create-time 2018-03-13
 */
public class Casquette extends HatDecorator {

    private Person person;

    public Casquette(Person person) {
        this.person = person;
    }

    @Override
    public String getDescription() {
        return person.getDescription() + " a hat..";
    }

    @Override
    public double cost() {
        return 75 + person.cost();
    }
}
