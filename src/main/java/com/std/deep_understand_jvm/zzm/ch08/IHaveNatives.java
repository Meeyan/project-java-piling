package com.std.deep_understand_jvm.zzm.ch08;

/**
 * 模拟 native 关键字方法
 *
 * @author zhaojy
 * @date 2020/8/17 6:08 下午
 */
public class IHaveNatives {

    public native void nativeMethod1(int x);

    public native static long nativeMethod();

    private native synchronized float nativeMethod3(Object o);

    native void nativeMethod4(int[] arr) throws Exception;

}
