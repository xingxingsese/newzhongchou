package com.lsc.bean;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @Description: Mock代码Bean
 * @Author: lisc
 * @date: 2022/5/9
 */
@Builder
@Data
public class MockClassBean {

    // 包名
    private String packageName;

    // 全类名
    private String pack;

    // 当前类名
    private String className;

    // 当前类属性
    private List<MockAttributeBean> attribute;

    // 当前类内的方法
    private List<MockMethodBean> methodBeansList;
}
