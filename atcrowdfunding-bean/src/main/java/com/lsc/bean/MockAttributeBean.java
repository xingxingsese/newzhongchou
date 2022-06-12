package com.lsc.bean;

import lombok.Builder;
import lombok.Data;

/**
 * @Description: 属性对象
 * @Author: lisc
 * @date: 2022/5/11
 */
@Data
@Builder
public class MockAttributeBean {
    /**
     * 属性名
     */
    private String attributeName;

    /**
     * 属性类型
     */
    private String attributeType;
}
