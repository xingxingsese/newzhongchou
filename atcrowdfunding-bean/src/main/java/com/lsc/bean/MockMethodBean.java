package com.lsc.bean;

import lombok.Data;

import java.util.List;

/**
 * @Description:
 * @Author: lisc
 * @date: 2022/5/9
 */
@Data
public class MockMethodBean {
    // 方法名
    private String methodName;
    // 方法入参
    private List<String> parameterType;
    // 方法返回值类型
    private String returnType;
}
