package com.std.gof23.L09_decorator;

/**
 * 抽象装饰器【衣服装饰器】，可以通过扩展，制造更多的装饰类型
 *
 * @author zhaojy
 * @date 2018-03-13
 */
public abstract class ClothingDecorator extends Person {
    public abstract String getDescription();
}
