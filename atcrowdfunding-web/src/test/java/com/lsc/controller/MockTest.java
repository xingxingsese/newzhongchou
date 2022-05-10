package com.lsc.controller;

import com.alibaba.fastjson.JSONObject;
import com.lsc.freemarker.utils.JarLoaderUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Date;
import java.util.Random;

/**
 * @Description:
 * @Author: lisc
 * @date: 2022/5/8
 */

public class MockTest {
    private static final Logger log = LoggerFactory.getLogger(MockTest.class);

    @Test
    public void test() {

    }

    @Test
    public void classPathCreateMock() {
        // String jarPath = "D:\\Maven-3.5.4\\Maven\\RepMaven\\com\\bestpay\\riskcontrol\\risk-account-auth-api\\1.14.0-SNAPSHOT\\risk-account-auth-api-1.14.0-20210720.112843-7.jar";
        String jarPath = "D:\\Maven-3.5.4\\Maven\\RepMaven\\com\\bestpay\\riskcontrol";
        String classNamePath = "com.bestpay.riskastrade.manager.service.RiskAsTradeDictCacheServiceImpl";
        String outPath = "C:\\Users\\zuiho\\Desktop";

        try {
            // 根据jar包路径和要获取的class全类名获取反射class对象
          /*  Class<?> aClass = JarLoaderUtils.getClassObject(jarPath, classNamePath);
            if (aClass == null) {
                log.info("Class为null");
                return;
            }*/
            ClassLoader classLoader = ClassLoader.getSystemClassLoader();
            Class<?> aClass = classLoader.loadClass("com.lsc.controller.AdminCrudController");

            // 全类名
            String classPathName = aClass.getName();
            int lastIndexOf = classNamePath.lastIndexOf('.');
            // 类名
            String className = classNamePath.substring(lastIndexOf + 1);
            // 包名
            String packageName = classNamePath.substring(0, lastIndexOf);

            // 得到类下所有属性
            Field[] declaredFields = aClass.getDeclaredFields();
            log.info("获取当前类的所有属性:start");
            for (Field field : declaredFields) {
                // 设置属性可访问
                field.setAccessible(true);
                // 属性名
                log.info(field.getName());
                // 属性类型
                log.info(field.getType().getSimpleName());

            }
            log.info("获取当前类的所有属性:end");


            // 得到类下所有方法 不包括继承的
            for (Method method : aClass.getDeclaredMethods()) {
                // 方法名
                String methodName = method.getName();
                System.out.println("methodName = " + methodName);

                Parameter[] parameters = method.getParameters();
                for (Parameter parameter : parameters) {
                    // arg0
                    System.out.println("parameters = " + parameter.getName());

                    System.out.println("parameter.getType() = " + parameter.getType().getSimpleName());
                }


                Class<?>[] parameterTypes = method.getParameterTypes();
                for (Class<?> parameterType : parameterTypes) {
                    // 判断当前参数是否是基本类型
                    if (!JarLoaderUtils.isWrapClass(parameterType)) {
                        // 把方法入参生成json文件到指定目录下
                        JarLoaderUtils.CreationJsonText(parameterType, outPath);
                    }
                }
            }

        } catch (Exception e) {
            log.info("错误异常e :{} message:{}", e, e.getMessage());

        }
    }


}
