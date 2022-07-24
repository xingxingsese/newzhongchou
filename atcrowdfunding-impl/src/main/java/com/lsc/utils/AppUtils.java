package com.lsc.utils;

import org.springframework.util.DigestUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description:
 * @Author: lisc
 * @date: 2021/12/28
 */
public class AppUtils {
    public static String getDigestPwd(String str) {
        return DigestUtils.md5DigestAsHex(str.getBytes());
    }

    public static String getCurrentTime() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return simpleDateFormat.format(date);
    }
}
