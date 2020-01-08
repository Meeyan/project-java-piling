package com.std.thread.chp2;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.math.BigInteger;

/**
 *
 */
@ThreadSafe
public class CachedFactorizer extends GenericServlet {
    @GuardedBy("this")
    private BigInteger lastNumber;

    @GuardedBy("this")
    private BigInteger[] lastFactors;

    @GuardedBy("this")
    private long hits;

    @GuardedBy("this")
    private long cacheHits;

    /**
     * 加锁
     *
     * @return
     */
    public synchronized long getHits() {
        return hits;
    }

    /**
     * 加锁
     *
     * @return
     */
    public synchronized double getCacheHitsRadio() {
        return (double) cacheHits / (double) hits;
    }

    /**
     * 改进后：synchronized不在使用在service方法上，而实际的开发中，可以将一
     * 些非需要同步的，耗时的代码放到同步代码块外，以提高性能。
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     * @author zhaojy
     * @date 2017-03-24
     */
    @Override
    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        System.out.println("姓名：" + name + "，来访了");
        BigInteger i = TestUtil.extractFromRequest(request);
        BigInteger[] factors = null;
        synchronized (this) {
            ++hits;
            if (i.equals(lastNumber)) {
                ++cacheHits;
                factors = lastFactors.clone();
            }
        }

        if (factors == null) {
            factors = TestUtil.factor(i);
            synchronized (this) {
                lastNumber = i;
                lastFactors = factors.clone();
            }
        }

        TestUtil.encodeIntoResponse(response, factors);
    }
}
