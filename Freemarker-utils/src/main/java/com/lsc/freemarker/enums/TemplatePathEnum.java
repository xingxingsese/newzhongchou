package com.lsc.freemarker.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author lisc
 * @Description: com.lsc.freemarker.enums
 * 模板路径枚举
 * @date 2022/11/11 11:18
 */
@AllArgsConstructor
@Getter
public enum TemplatePathEnum {
    TABLE_SQL_XML("D:\\Code\\TestCode\\newzhongchou\\Freemarker-utils\\src\\main\\resources\\FreeMarkerFile\\dataBaseTemplates", "数据库sql生成路径"),
    SET_METHOD_PATH("D:\\Code\\TestCode\\newzhongchou\\Freemarker-utils\\src\\main\\resources\\FreeMarkerFile\\CodeTemplates\\setMethod", "set方法模板路径"),
    MOCK_TEMPLATE_PATH("D:\\Code\\TestCode\\newzhongchou\\Freemarker-utils\\src\\main\\resources\\FreeMarkerFile\\mock-templates", "MOCK代码的模板"),

    ;
    private String path;

    private String desc;

    public static ResultCodeEnum getByCode(String code) {
        return Arrays.stream(ResultCodeEnum.values()).filter(value -> value.getCode().equals(code)).findFirst()
                .orElse(null);
    }
}
