package com.lsc.freemarker.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author lisc
 * @Description: com.lsc.freemarker.enums
 * 生成代码结果路径枚举
 * @date 2022/11/11 11:18
 */

@Getter
@AllArgsConstructor
public enum ResultOutPathEnum {
    /**
     * 数据库sql生成路径
     */
    TABLE_RESULT_PATH("D:\\Code\\TestCode\\newzhongchou\\Freemarker-utils\\src\\main\\resources\\FreeMarkerFile\\SpannedFile\\table", "数据库sql生成文件输出路径"),
    /**
     * set方法生成路径
     */
    SET_METHOD_PATH("D:\\Code\\TestCode\\newzhongchou\\Freemarker-utils\\src\\main\\resources\\FreeMarkerFile\\SpannedFile\\set", "set方法文件生成路径"),

    /**
     * mock代码生成路径
     */
    MOCK_RESULT_PATH("D:\\Code\\TestCode\\newzhongchou\\Freemarker-utils\\src\\main\\resources\\FreeMarkerFile\\SpannedFile\\mock\\", "Mock代码生成路径"),


    ;
    /**
     * 路径
     */
    private String path;

    /**
     * 描述
     */
    private String desc;


    public static ResultCodeEnum getByCode(String code) {
        return Arrays.stream(ResultCodeEnum.values()).filter(value -> value.getCode().equals(code)).findFirst()
                .orElse(null);
    }


}
