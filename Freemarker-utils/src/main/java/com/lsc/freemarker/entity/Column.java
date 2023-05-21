package com.lsc.freemarker.entity;

import lombok.Data;

/**
 * Created by lisc on 2021/12/19
 */
@Data
public class Column {
    // 列名称
    private String columnName;
    // 列名称(处理后的列名称)
    private String javaColumnName;
    // 列类型
    private String columnType;
    // 列数据库类型
    private String columnDbType;
    // 列描述
    private String columnComment;
    // 是否是主键
    private String columnKey;
}
