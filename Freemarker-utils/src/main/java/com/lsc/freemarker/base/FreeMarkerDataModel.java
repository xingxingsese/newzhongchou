package com.lsc.freemarker.base;

/**
 * @author lisc
 * @Description: com.lsc.freemarker
 * @date 2022/11/11 17:48
 */
public interface FreeMarkerDataModel<T> {

    /**
     * 构建数据模型的数据
     */
    T buildModelData() throws Exception;
}
