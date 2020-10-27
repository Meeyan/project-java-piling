package com.demo.advice_150;

import cn.hutool.crypto.SecureUtil;

/**
 * @author zhaojy
 * @date 2020/10/27 2:48 下午
 */
public class SecurityTest {
    public static void main(String[] args) {
        System.out.println(SecureUtil.sha1("aba12131"));
    }
}
