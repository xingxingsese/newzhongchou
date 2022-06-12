package com.lsc.controller;

import com.alibaba.fastjson.JSONObject;
import com.lsc.bean.MockAttributeBean;
import com.lsc.bean.MockClassBean;
import com.lsc.bean.MockMethodBean;
import com.lsc.freemarker.utils.JarLoaderUtils;
import com.lsc.freemarker.utils.StringBuildUtils;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.function.Consumer;

/**
 * @Description:
 * @Author: lisc
 * @date: 2022/5/8
 */

public class MockTest {
    private static final Logger log = LoggerFactory.getLogger(MockTest.class);

    @Test
    public void test() {
        List listMock = Mockito.mock(List.class);
        Mockito.doAnswer(new Answer(){
            @Override
            public Object answer(InvocationOnMock invocationOnMock)throws Throwable {
              Object argument = invocationOnMock.getArguments();
                Consumer.class.getDeclaredMethod("accept",Object.class).invoke(argument,1);
                return argument;
            }
        }).when(listMock).forEach(Mockito.any());

    }

    @Test
    public void classPathCreateMock() {
        // String jarPath = "D:\\Maven-3.5.4\\Maven\\RepMaven\\com\\bestpay\\riskcontrol\\risk-account-auth-api\\1.14.0-SNAPSHOT\\risk-account-auth-api-1.14.0-20210720.112843-7.jar";
        String jarPath = "D:\\Maven-3.5.4\\Maven\\RepMaven\\com\\bestpay\\riskcontrol";
        String classNamePath = "com.bestpay.riskastrade.manager.service.RiskAsTradeDictCacheServiceImpl";
        String outPath = "C:\\Users\\zuiho\\Desktop";

        try {
            // 根据jar包路径和要获取的class全类名获取反射class对象
            Class<?> aClass = JarLoaderUtils.getClassObject(jarPath, classNamePath);
            if (aClass == null) {
                log.info("Class为null");
                return;
            }
          /*  ClassLoader classLoader = ClassLoader.getSystemClassLoader();
            Class<?> aClass = classLoader.loadClass("com.lsc.controller.AdminCrudController");*/

            MockClassBean mockClassBean = JarLoaderUtils.buildSourceData(classNamePath, outPath, aClass);


        } catch (Exception e) {
            log.info("错误异常e :{} message:{}", e, e.getMessage());

        }
    }




}
