package com.std.servlet;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 没明白程序的意义是什么
 *
 * @author zhaojy
 * @createTime 2017-03-23
 */
public class UnsafeCachingFactorizer extends GenericServlet {

    private final AtomicReference<BigInteger> lastNumber = new AtomicReference<BigInteger>();
    private final AtomicReference<BigInteger[]> lastFactorizers = new AtomicReference<BigInteger[]>();


    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        BigInteger i = extractFromRequest(servletRequest);
        if (i.equals(lastNumber.get())) {
            encodeIntoResponse(servletResponse, lastFactorizers.get()); // 有什么卵用？
        } else {
            BigInteger[] factors = factor(i);
            lastNumber.set(i);
            lastFactorizers.set(factors);
            encodeIntoResponse(servletResponse, factors);           // 有什么卵用？
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
