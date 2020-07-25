package com.std.deeperUnderstandJVM.zzm.ch04;


/**
 * staticObj，instanceObj，localObj 存放在哪里？
 *
 * @author zhaojy
 * @date 2020/7/23 6:37 PM
 */
public class JHSDB_Test {
    static class Test {
        static ObjectHolder staticObj = new ObjectHolder();
        ObjectHolder instanceObj = new ObjectHolder();

        void foo() {
            ObjectHolder localObj = new ObjectHolder();

            // 此处可以设置一个断点，便于观察
            System.out.println("done");
        }

    }

    private static class ObjectHolder {
    }

    public static void main(String[] args) {
        Test test = new Test();
        test.foo();
    }

}
