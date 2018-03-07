package com.std.jvm;

/**
 * finalize后，对象还能被复活，finalize方法在开发中要慎用
 * @author zhaojy
 * @create-time 2018-02-26
 */
public class D4_CanReliveObj {
    static D4_CanReliveObj obj;

    /**
     * 只会调用一次，避免使用该方法（gc时会被调用）
     * @throws Throwable
     */
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("D4_CanReliveObj finalize called");
        obj = this;
    }

    @Override
    public String toString() {
        return "I am CanReliveObj";
    }

    public static void main(String[] args) throws InterruptedException {
        obj = new D4_CanReliveObj();
        obj = null; // 可复活
        System.gc();
        Thread.sleep(1000);
        if (obj == null) {
            System.out.println("obj is null");
        } else {
            System.out.println("obj 可用");
        }

        System.out.println("第二次gc");
        obj = null;
        System.gc();
        Thread.sleep(1000);
        if (obj == null) {
            System.out.println("obj is null");
        } else {
            System.out.println("obj 可用");
        }
    }
}
