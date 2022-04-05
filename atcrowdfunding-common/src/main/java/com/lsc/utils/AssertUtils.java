package com.lsc.utils;

import com.lsc.utils.ExceptionUtils.LscException;

/**
 * @Description:
 * @Author: lisc
 * @date: 2022/3/17
 */
public class AssertUtils {

    public static void isTrue(final boolean expvalue,final ExceptionCode code, String msg){
        if (!expvalue){
            new LscException(code,msg);
        }
    }
}
