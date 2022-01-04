package com.lsc.com.lsc.utils;

import org.springframework.util.DigestUtils;

/**
 * @Description:
 * @Author: lisc
 * @date: 2021/12/28
 */
public class AppUtils {
    public static String getDigestPwd(String str){
        return DigestUtils.md5DigestAsHex(str.getBytes());
    }
}
