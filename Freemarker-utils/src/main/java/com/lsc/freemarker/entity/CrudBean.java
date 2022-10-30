package com.lsc.freemarker.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lisc
 * @Description: com.lsc.freemarker.entity
 * @date 2022/10/24 16:45
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CrudBean {
    /**
     * 项目路径
     * com.ipay.ibizecoprod
     */
    private String rootPath;

    /**
     * 包路径
     * core.service.voyage.impl
     */
    private String packagePath;

    /**
     * 类名
     */
    private String className;

    /**
     * 表名
     */
    private String tableName;
}
