package com.std.thread.chp2;

import net.jcip.annotations.GuardedBy;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.math.BigInteger;

/**
 * 线程安全测试,
 * 可以确保为线程安全，但是性能不行
 *
 * @author zhaojy
 * @createTime 2017-03-24
 */
public class SynchronizedFactorzier extends GenericServlet {

    @GuardedBy("this")
    private BigInteger lastNumber;

    @GuardedBy("this")
    private BigInteger[] lastFactors;

    /**
     * 加锁
     *
     * @param servletRequest
     * @param servletResponse
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public synchronized void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        BigInteger i = extractFromRequest(servletRequest);  // 获取一个BigInteger 7
        System.out.println(Thread.currentThread().getName());
        // 判断lastNumber和i是否相同
        if (i.equals(lastNumber)) {
            System.out.println("因子值相同，不用做初始化");
            // 相同啥也不做？
            encodeIntoResponse(servletResponse, lastFactors);
        } else {
            System.out.println("初始化因子值");
            // 不同，做值初始化
            BigInteger[] factors = factor(i);
            lastNumber = i;
            lastFactors = factors;
            encodeIntoResponse(servletResponse, lastFactors);
        }
    }


    void encodeIntoResponse(ServletResponse resp, BigInteger[] factors) {
    }

    BigInteger extractFromRequest(ServletRequest req) {
        return new BigInteger("7");
    }

    BigInteger[] factor(BigInteger i) {
        // Doesn't really factor
        return new BigInteger[]{i};
    }
}
