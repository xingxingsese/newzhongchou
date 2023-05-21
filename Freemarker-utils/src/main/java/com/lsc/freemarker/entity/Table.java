package com.lsc.freemarker.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by lisc on 2021/12/19
 */

@Data
public class Table {
    // 数据库表名
    private String databaseTableName;
    // 处理后的表名
    private String javaTableName;
    // 描述
    private String comment;
    // 主键列
    private String key;
    // 列集合
    private List<Column> columns;
}
