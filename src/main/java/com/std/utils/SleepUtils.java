package com.std.utils;

import java.util.concurrent.TimeUnit;

/**
 * 休眠工具类
 *
 * @author zhaojy
 * @date 2019-07-01 19:01
 */
public class SleepUtils {
    public static void second(int second) {
        try {
            TimeUnit.SECONDS.sleep(second);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
