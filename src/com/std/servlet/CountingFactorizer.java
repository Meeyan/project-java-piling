package com.std.servlet;

import net.jcip.annotations.ThreadSafe;

import javax.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 测试线程安全<p>
 * 注意：<p>
 * 如果不使用@ThreadSafe注解:<p>
 * 1.AtomicLong可以保证为线程安全（原子线程安全）的，但仅限该对象<p>
 * 2.secCnt,线程非安全，会有问题。<p>
 * 使用@ThreadSafe注解：<p>
 * 1.AtomicLong可以保证为线程安全<p>
 * 2.secCnt,线程非安全，会有问题。需要其他线程安全机制保证同步<p>
 */
@ThreadSafe
public class CountingFactorizer extends GenericServlet implements Servlet {
    private final AtomicLong count = new AtomicLong(0);  // 原子类型Long

    private Long secCnt = 0L;

    public Long getCount() {
        return count.get();
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        long curCnt = count.incrementAndGet();
        secCnt++;
        PrintWriter writer = servletResponse.getWriter();
        writer.write("current Cnt is : " + curCnt + ",secCnt is:" + secCnt);
        writer.close();
    }
}
