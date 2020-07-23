package com.std.srljjxnj.zzm.ch04;


/**
 * @author zhaojy
 * @date 2020/7/23 6:37 PM
 */
public class JHSDB_Test {
    static class Test {
        static ObjectHolder staticObj = new ObjectHolder();
        ObjectHolder instanceObj = new ObjectHolder();

        void foo() {
            ObjectHolder localObj = new ObjectHolder();
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
