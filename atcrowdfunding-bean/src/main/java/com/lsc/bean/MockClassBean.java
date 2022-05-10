package com.lsc.bean;

import lombok.Data;

import java.util.List;

/**
 * @Description:
 * @Author: lisc
 * @date: 2022/5/9
 */
@Data
public class MockClassBean {

    // 当前类名
    private String className;

    // 当前类属性
    private String attributeTypes;
    // 包名
    private String packageName;

    // 全类名
    private String pack;

    // 当前类内的方法
    private List<MockMethodBean> methodBeansList;
}
