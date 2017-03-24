package com.std.test;

import sun.misc.BASE64Encoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Created by zhaojy on 2017/3/22.
 */
public class SignUtil {
    public static void main(String[] args) throws Exception {
       /* String str = "test";
        String ret = SignUtil.SHA1(str);
        System.out.println(ret);
        String fin = SignUtil.base64Encode(ret);
        System.out.println(fin);*/

        String key = "kmfghnbf09876512qwds78ikuyjmp0o2";
        String name = "张三";
        String phone = "18618880001";
        String[] paraArr = new String[]{name, phone, key};
        Arrays.sort(paraArr);
        String retStr = SignUtil.ArrayToString(paraArr);

        System.out.println(retStr);
    }

    public static String genSign(String str) {
        try {
            return SignUtil.base64Encode(SignUtil.SHA1(str));
        } catch (Exception e) {
            // 处理异常
            return "";
        }
    }


    /**
     * 数组转换为字符串
     *
     * @param strArr
     * @return
     */
    public static String ArrayToString(String[] strArr) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < strArr.length; i++) {
            sb.append(strArr[i]);
        }
        return sb.toString();
    }


    /**
     * sha-1签名
     *
     * @param decript
     * @return
     * @throws Exception
     */
    public static String SHA1(String decript) throws Exception {
        try {
            MessageDigest digest = java.security.MessageDigest.getInstance("SHA-1");
            digest.update(decript.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * base64加密
     *
     * @param string
     * @return
     * @throws Exception
     */
    public static String base64Encode(String string) throws Exception {
        BASE64Encoder base64Encoder = new BASE64Encoder();
        return base64Encoder.encode(string.getBytes("utf-8"));
    }
}
