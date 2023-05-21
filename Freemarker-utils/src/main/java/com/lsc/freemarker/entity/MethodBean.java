package com.lsc.freemarker.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author lisc
 * @Description: com.lsc.freemarker.entity
 * @date 2022/10/9 11:12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MethodBean {
    /**
     * .
     * 方法名
     */
    private String methodName;

    /**
     * 方法返回值类型
     */
    private List<FieldBean> methodResponseType;

    /**
     * 方法入参类型
     */
    private List<FieldBean> methodRequestType;
}
