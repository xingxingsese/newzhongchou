package com.lsc.common.ExceptionUtils;

import com.lsc.common.ExceptionCode;

/**
 * @Description:
 * @Author: lisc
 * @date: 2022/3/17
 */
public class LscException extends RuntimeException {
    private ExceptionCode code;
    private String msg;

    public LscException(String message) {
        super(message);
        this.msg = message;
    }

    public LscException(ExceptionCode code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }


    public ExceptionCode getCode() {
        return code;
    }

    public void setCode(ExceptionCode code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
