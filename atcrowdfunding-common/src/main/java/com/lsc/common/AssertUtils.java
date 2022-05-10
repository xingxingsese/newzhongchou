package com.lsc.common;

import com.lsc.common.ExceptionUtils.LscException;

/**
 * @Description:
 * @Author: lisc
 * @date: 2022/3/17
 */
public class AssertUtils {

    public static void isTrue(final boolean expvalue, final ExceptionCode code, String msg) {
        if (!expvalue) {
            throw new LscException(code, msg);
        }
    }
}
