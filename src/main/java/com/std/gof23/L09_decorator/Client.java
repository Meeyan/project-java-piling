package com.std.gof23.L09_decorator;

/**
 * @author zhaojy
 * @date 2018-03-13
 */
public class Client {
    public static void main(String[] args) {
        // 先创建一个Teenager对象，接着用Shirt装饰它，就变成了穿着Shirt的Teenager，
        // 再用Casquette装饰，就变成了戴着Casquette的穿着Shirt的Teenager。
        // 最终，Teenager被装饰城穿着Shirt，戴着Casquette
        Person person = new Teenager();
        person = new Shirt(person);
        person = new Casquette(person);
        System.out.println(person.getDescription() + "--" + person.cost());
    }
}
