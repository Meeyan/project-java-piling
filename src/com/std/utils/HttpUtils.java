package com.std.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * http请求工具类
 *
 * @author:zhaojy
 * @createTime:2016-04-22
 */
public class HttpUtils {

    /**
     * http工具类：参数请使用&方式拼接
     *
     * @param address 请求地址
     * @param content 内容
     * @return
     * @author:zhaojy
     * @createTime:2016-04-22
     */
    public static String httpReq(String address, String content, int method) {
        String retString = "";
        URL url = null;
        HttpURLConnection urlConn = null;
        try {
            /*得到url地址的URL类*/
            url = new URL(address);
            /*获得打开需要发送的url连接*/
            urlConn = (HttpURLConnection) url.openConnection();
            /*设置连接超时时间*/
            urlConn.setConnectTimeout(30000);
            /*设置读取响应超时时间*/
            urlConn.setReadTimeout(30000);
            if (method == 0) {
                /*设置post发送方式*/
                urlConn.setRequestMethod("POST");
            } else {
                /*设置get发送方式*/
                urlConn.setRequestMethod("GET");
            }

            /*发送commString*/
            urlConn.setDoOutput(true);
            OutputStream out = urlConn.getOutputStream();
            out.write(content.getBytes());
            out.flush();
            out.close();
            /*发送完毕 获取返回流，解析流数据*/
            BufferedReader rd = new BufferedReader(new InputStreamReader(urlConn.getInputStream(), "GBK"));
            StringBuffer sb = new StringBuffer();
            int ch;
            while ((ch = rd.read()) > -1) {
                sb.append((char) ch);
            }
            retString = sb.toString().trim();
            /*解析完毕关闭输入流*/
            rd.close();
        } catch (Exception e) {
            /*异常处理*/
            retString = "-107";
        } finally {
            /*关闭URL连接*/
            if (urlConn != null) {
                urlConn.disconnect();
            }
        }
        /*返回响应内容*/
        return retString;
    }
}
