package com.std.test;

import com.std.utils.HttpUtils;

/**
 * 多线程测试
 * 启动3个线程，访问非线程安全的服务，然后观察结果
 * 测试结果：3个线程，各内部发送100请求，结果为300，正确。
 *
 * @author:zhaojy
 * @createTime:2016-12-21
 */
public class TestSafeCnt implements Runnable {
    private String threadName;

    public TestSafeCnt(String threadName) {
        this.threadName = threadName;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            String rep = HttpUtils.httpReq("http://localhost:8080/safeCnt", "", 1);
            System.out.println(this.threadName + "-----" + rep);
        }
    }

    public static void main(String[] args) {
       /* Runnable run_1 = new TestSafeCnt("thread-1");
        Runnable run_2 = new TestSafeCnt("thread-2");
        Runnable run_3 = new TestSafeCnt("thread-3");
        new Thread(run_1).start();
        new Thread(run_2).start();
        new Thread(run_3).start();*/

        int i = Integer.MAX_VALUE + 11;
        System.out.println(Integer.toBinaryString(i));
        System.out.println(Integer.numberOfLeadingZeros(i));
    }


    /**
     * 解读：java.lang.Integer#numberOfLeadingZeros(int)，此处粘出来便于注解
     *
     * @param i
     * @return int
     */
    public static int numberOfLeadingZeros(int i) {
        if (i == 0)
            return 32;
        int n = 1;
        /*
         * 二分查找的思想
         *   1. 若右移16位为0，那么i的前16为都为0（接下来需要搞定，低16位中的前导0个数），所以将i左移16位，低16位变为高16位。
         */
        if (i >>> 16 == 0) {
            n += 16;
            i <<= 16;   // 左移并赋值
        }

        /*
         *  2.  若i右移24位为0，那么i的前8位都为0，所以左移8位，解决下面的24位中的前几位为0。
         */
        if (i >>> 24 == 0) {
            n += 8;
            i <<= 8;
        }
        if (i >>> 28 == 0) {
            n += 4;
            i <<= 4;
        }
        if (i >>> 30 == 0) {
            n += 2;
            i <<= 2;
        }

        /**
         * 第1位为符号位（0：正数，1：负数），负数的获得规则：将负数的绝对值二进制取反（32位）再加1，由此得得结果，其高位都是1，没有0。
         * 所以负数的前导0的个数为0个。
         * 如果i的第1位=0，第2位不为0，右移31位后，i为0，此时n=1；
         */
        n -= i >>> 31;
        return n;
    }

}
