package com.lsc.common;

/**
 * @Description:
 * @Author: lisc
 * @date: 2022/3/17
 */
public enum ExceptionCode {
    ACCOUNT_THRER_ARE("10000", "帐号存在"),
    EMAIL_THRER_ARE("10001", "邮箱存在"),
    CONDITION_IS_NULL("10003", "查询条件为null"),
    BUILD_FAIL("10004", "生成代码错误"),


    ;
    private String code;
    private String msg;

    ExceptionCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
