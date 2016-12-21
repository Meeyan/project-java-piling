package com.std.servlet;

import net.jcip.annotations.ThreadSafe;

import javax.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 测试线程安全
 */
@ThreadSafe
public class CountingFactorizer extends GenericServlet implements Servlet {
    private final AtomicLong count = new AtomicLong(0);  // 原子类型Long

    public Long getCount() {
        return count.get();
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        long curCnt = count.incrementAndGet();
        PrintWriter writer = servletResponse.getWriter();
        writer.write("current Cnt is : " + curCnt);
        writer.close();
    }
}
