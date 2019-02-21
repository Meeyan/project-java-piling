package com.std.advice_150.exm_006;

/**
 * 覆写变长方法要遵守规则
 *
 * @author zhaojy
 * @date 2019-02-20
 */
public class MainDemo {

    public static void main(String[] args) {

        // 向上转型
        Base base = new Sub();
        // 此处不会报错，因为编译器会将变长参数和数组视为一类。
        base.fun(100, 50);

        // 不转型
        Sub sub = new Sub();
        // 为什么编译会报错呢？
        // 此处使用变长参数方式传入，sub类中没有这样的方法，所以会报错
        // sub.fun(100, 50);

    }
}

class Base {
    /**
     * @param price
     * @param discounts 变长参数
     */
    void fun(int price, int... discounts) {
        System.out.println("Base .... fun");
    }
}

class Sub extends Base {
    /**
     * 注意此处，使用了数组写法
     *
     * @param price
     * @param discounts int[]
     */
    @Override
    void fun(int price, int[] discounts) {
        System.out.println("Sub .... fun");
    }
}