package com.std.deeperUnderstandJVM.zl.v006;

/**
 * 常量在编译阶段会存入到“调用这个常量的方法所在类”的常量池中
 * <p>
 * final 字段会存入 调用这个常量的方法所在类的常量池中
 * <p>
 * 本质上：调用类并没有直接引用到定义常量的类，因此不会触发定义常量的类的初始化
 * <p>
 * 注意：这里指的是将常量存放到 LoadFinalField 的常量池中，之后 LoadFinalField 与MyParent
 * <p>
 * 甚至：我们可以将编译好的MyParent.class文件删除，但是不影响程序运行
 * <p>
 * 助记符：
 * - ldc：表示将int，float或String类型的常量值从常量池推送到栈顶
 * - bipush: 表示将单字节（-128-127）的常量值推送至栈顶（short，byte）
 * - sipush: 表示将一个短整形（-32768-32767）推送至栈顶
 * - iconst_1 : 表示将int类型的数字1推送至栈顶（iconst_m1 ~ iconst_5，-1~5，共7个，只有这么多，因为常用）
 *
 * @author zhaojy
 * @date 2020/4/19 15:56
 */
public class LoadFinalField {
    public static void main(String[] args) {
        System.out.println(MyParent.b);
        System.out.println(MyParent.i);
    }
}

class MyParent {
    public static final String str = "hello Parent Str";

    public static final short b = 812;

    public static final int i = 10;

    static {
        System.out.println("MyParent static block");
    }
}
