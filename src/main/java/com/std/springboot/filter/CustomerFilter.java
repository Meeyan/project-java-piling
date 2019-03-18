package com.std.springboot.filter;


import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * 自定义过滤器 需要实现Filter
 *
 * @author zhaojy
 * @date 2019-03-18
 */
@WebFilter(urlPatterns = "/user/*")
@Slf4j
public class CustomerFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws
            IOException, ServletException {
        log.info("-----------");
        System.out.println("");
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
