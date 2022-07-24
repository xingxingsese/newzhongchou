package com.lsc.common.encryptionutils;

/**
 * @Description: Base64 编码是我们程序开发中经常使用到的编码方法，
 * 64 个可打印字符来表示二进制数据。这 64 个字符是：
 * 小写字母 a-z、大写字母 A-Z、数字 0-9、符号"+“、”/“（再加上作为垫字的”="，实际上是 65 个字符），
 * 其他所有符号都转换成这个字符集中的字符。Base64 编码通常用作存储、传输一些二进制数据编码方法，所以说它本质上是一种将二进制数据转成文本数据的方案。
 * @Author: lisc
 * @date: 2022/7/23
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;

public class Base64Util {

    private static final Logger log = LoggerFactory.getLogger(Base64Util.class);

    /***
     * BASE64解密
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryBASE64(String key) {
        byte[] bytes = new byte[0];
        try {
            bytes = new BASE64Decoder().decodeBuffer(key);
        } catch (IOException e) {
            log.error("Base64Util:decryBASE64 发生异常 e: {}", e.getStackTrace(), e);
        }
        return bytes;
    }

    /***
     * BASE64加密
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptBASE64(byte[] key) {
        return (new BASE64Encoder()).encode(key);
    }
}
