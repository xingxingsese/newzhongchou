package com.lsc.freemarker.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author lisc
 * @Description: 方法结果枚举
 * @date 2022/11/7 18:05
 */
@AllArgsConstructor
@Getter
public enum ResultCodeEnum {
    SUCCESS("1", true, "执行成功"),

    FAILURE("0", false, "执行失败");


    private String code;

    private Boolean success;

    private String desc;

    public static ResultCodeEnum getByCode(String code) {
        return Arrays.stream(ResultCodeEnum.values()).filter(value -> value.getCode().equals(code)).findFirst()
                .orElse(null);
    }

}
