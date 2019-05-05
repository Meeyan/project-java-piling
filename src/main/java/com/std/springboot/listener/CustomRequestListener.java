package com.std.springboot.listener;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;

/**
 * 自定义监听器 - 监听request初始化和销毁
 *
 * @author zhaojy
 */
@WebListener
@Slf4j
public class CustomRequestListener implements ServletRequestListener {

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        log.info("---------request listener destroyed---------");
    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        log.info("---------request listener initialized---------");
    }
}
