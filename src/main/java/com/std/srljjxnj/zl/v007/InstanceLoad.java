package com.std.srljjxnj.zl.v007;

/**
 * 对于数组类型说，其类型是由JVM在运行期动态生成的，表示为  [Lcom.std.srljjxnj.zl.v007.MyParentIns; 这种形式。
 * 动态生成的类型，其父类型就是Object
 * 对于数组来说，JavaDoc经常将构成数组的元素为Component，实际上就是将数组降低为一个维度后的类型
 * <p>
 * 助记符：
 * - anewarray: 表示创建一个引用类型的（如类，解耦，数组）数组，并将其引用值压入栈顶
 * - newarray: 表示创建一个指定的原始类型（如int，short，float等）的数组，并将其引用值压入栈顶
 *
 * @author zhaojy
 * @date 2020/4/19 17:13
 */
public class InstanceLoad {
    public static void main(String[] args) {
        // 会触发MyParentIns静态初始化，但是只会初始化一次
        //MyParentIns myParentIns = new MyParentIns();

        // 不会触发MyParentIns的静态初始化，因为没有主动引用类
        MyParentIns[] myArr = new MyParentIns[1];
        System.out.println(myArr.getClass());
    }
}


class MyParentIns {
    static {
        System.out.println("MyParentIns static block");
    }
}