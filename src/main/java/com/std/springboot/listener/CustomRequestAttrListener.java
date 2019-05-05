package com.std.springboot.listener;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.annotation.WebListener;

/**
 * 自定义监听器 - 监听request属性的add，replace，remove
 *
 * @author zhaojy
 */
@WebListener
@Slf4j
public class CustomRequestAttrListener implements ServletRequestAttributeListener {
    @Override
    public void attributeAdded(ServletRequestAttributeEvent srae) {
        log.info("---------request attribute - Added---------");
    }

    @Override
    public void attributeRemoved(ServletRequestAttributeEvent srae) {
        log.info("---------request attribute - Removed---------");
    }

    @Override
    public void attributeReplaced(ServletRequestAttributeEvent srae) {
        log.info("---------request attribute - Replaced---------");
    }
}
