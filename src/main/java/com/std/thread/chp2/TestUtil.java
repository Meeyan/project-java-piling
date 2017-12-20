package com.std.thread.chp2;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.math.BigInteger;

/**
 * 工具类
 */
public class TestUtil {

    public static void encodeIntoResponse(ServletResponse resp, BigInteger[] factors) {
    }

    public static BigInteger extractFromRequest(ServletRequest req) {
        return new BigInteger("7");
    }

    public static BigInteger[] factor(BigInteger i) {
        return new BigInteger[]{i};
    }
}
