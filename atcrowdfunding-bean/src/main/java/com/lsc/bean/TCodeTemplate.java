package com.lsc.bean;

import lombok.Data;

import java.util.Date;

@Data
public class TCodeTemplate {
    // 模版id
    private Integer id;

    // 模版名称
    private String templateName;

    // 模版内容
    private String templateText;

    // 代码输出路径
    private String outPath;

    // 元数据json文本
    private String metaDataJsonText;

    // 模版类型  mock 还是javacode
    private Integer type;

    // 创建时间
    private Date createDate;

    // 创建人
    private String author;

    // 更新时间
    private Date updateDate;

    // 用户id
    private String userId;


}