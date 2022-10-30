package com.lsc.freemarker.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author lisc
 * @Description: com.lsc.freemarker.entity
 * @date 2022/10/9 11:03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FreeMarkerDataBean {

    /**
     * 全类名只要类名
     * com.lsc.freemarker.entity.FreeMarkerDataBean
     * FreeMarkerDataBean
     */
    private String className;

    /**
     * 全类名不要类名
     * com.lsc.freemarker.entity.FreeMarkerDataBean
     * com.lsc.freemarker.entity
     */
    private String classPath;
    /**
     * 属性类型
     */
    private List<FieldBean> fieldBeanList;

    /**
     * 方法
     */
    private List<MethodBean> methodBeanList;
}
