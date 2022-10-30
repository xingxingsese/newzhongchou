package com.lsc.freemarker.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lisc
 * @Description: com.lsc.freemarker.entity
 * @date 2022/10/12 18:34
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FieldBean {
    /**
     * 属性类型
     */
    private String fieldType;
    /**
     * 属性名
     */
    private String fieldName;
    /**
     * 属性全路径
     */
    private String fieldPathAndName;
}
