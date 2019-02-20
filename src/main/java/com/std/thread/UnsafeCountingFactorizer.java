package com.std.thread;


import net.jcip.annotations.NotThreadSafe;

import javax.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;

/**
 * 测试非线程安全
 *
 * @author:zhaojy
 * @createTime:2016-12-21
 */
@NotThreadSafe
public class UnsafeCountingFactorizer implements Servlet {
    private Long count = 0L;

    public Long getCount() {
        return count;
    }

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {

    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void destroy() {

    }

    /**
     * 服务方法
     *
     * @param servletRequest
     * @param servletResponse
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        BigInteger i = extractFromRequest(servletRequest);
        BigInteger[] factors = factor(i);
        ++count;
        encodeIntoResponse(servletResponse, factors);
        servletResponse.setCharacterEncoding("utf-8");
        PrintWriter writer = servletResponse.getWriter();
        writer.write("current count is :" + getCount().toString());
        writer.close();
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    void encodeIntoResponse(ServletResponse res, BigInteger[] factors) {
    }

    BigInteger extractFromRequest(ServletRequest req) {
        return new BigInteger("7");
    }

    BigInteger[] factor(BigInteger i) {
        // Doesn't really factor
        return new BigInteger[]{i};
    }
}
