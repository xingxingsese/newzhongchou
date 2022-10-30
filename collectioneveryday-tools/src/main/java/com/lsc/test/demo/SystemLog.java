package com.lsc.test.demo;

import java.lang.annotation.*;

/**
 * @author lisc
 * @Description: 切面注解
 * @date 2022/10/27 18:15
 */
@Retention(RetentionPolicy.RUNTIME) //注解保留期限
@Target(ElementType.METHOD) //注解目标类型
@Documented
public @interface SystemLog {
    int LogType() default Constants.LogType.Empty;
}

