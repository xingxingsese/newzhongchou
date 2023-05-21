package com.lsc.freemarker.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lisc
 * @Description: com.lsc.freemarker.entity
 * @date 2022/11/11 17:06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MockResult<T> implements Serializable {

    /**
     * 结果标识
     */
    private String code;
    /**
     * 接口成败标识
     */
    private boolean success;
    /**
     * 错误信息
     */
    private String errorMessage;

    /**
     * 返回的数据
     */
    private T data;

    /**
     * 获取默认的success result
     *
     * @param data 数据
     * @param <T>  data范型
     * @return success result
     */
    public static <T> MockResult<T> buildSuccessResult(T data) {
        MockResult<T> result = new MockResult<>();
        result.setSuccess(true);
        result.setData(data);

        return result;
    }

    public static <T> MockResult<T> processBaseResult(T data) {
        MockResult<T> result = new MockResult<>();
        result.setSuccess(true);
        result.setData(data);
        return result;
    }
}
