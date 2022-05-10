package com.lsc.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description:
 * @Author: lisc
 * @date: 2022/5/7
 */
@Data
@NoArgsConstructor
public class CodeBean {
    // 项目路径  com.lsc
    private String projectPath;
    // 类名
    private String className;
    // 输出路径
    private String outPath;
    // mapper类名
    private String mapClass;

}
