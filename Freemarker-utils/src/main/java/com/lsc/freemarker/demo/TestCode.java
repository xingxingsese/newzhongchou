package com.lsc.freemarker.demo;

import com.alibaba.fastjson.JSONObject;
import com.lsc.freemarker.core.CustomClassLoader;
import com.lsc.freemarker.entity.Bean;
import com.lsc.freemarker.entity.CrudBean;
import com.lsc.freemarker.enums.ResultCodeEnum;
import com.lsc.freemarker.utils.ReflectionUtil;
import freemarker.template.Configuration;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Function;

/**
 * @author lisc
 * @Description: com.lsc.freemarker.demo
 * @date 2022/10/10 10:08
 */
public class TestCode {

    @Test
    public void test01() throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {

        //    String classPath = "com.ipay.ibizecoprod.core.service.voyage.impl.MpOpenMarketPlaceServiceImpl";
        String classPath = "com.ipay.ibizecoprod.core.service.voyage.impl.MpOpenMarketPlaceServiceImpl";
        // 创建自定义的类加载器
        CustomClassLoader loader = new CustomClassLoader();

        // 使用自定义的类加载器加载TestHelloWorld类
        Class classaa = loader.loadClass(classPath);
        Field[] declaredFields = classaa.getDeclaredFields();
        Method[] declaredMethods = classaa.getDeclaredMethods();
        Method[] methods = classaa.getMethods();

        for (Method declaredMethod : declaredMethods) {
            declaredMethod.setAccessible(true);

            System.out.println("declaredMethod = " + JSONObject.toJSONString(declaredMethod));
       /*     for (Type genericParameterType : declaredMethod.getGenericParameterTypes()) {
                String typeName = genericParameterType.getTypeName();

            }*/
        }

        System.out.println(JSONObject.toJSONString(classaa));
        System.out.println("declaredFields " + JSONObject.toJSONString(declaredFields));
        System.out.println("declaredMethods " + JSONObject.toJSONString(declaredMethods));
        System.out.println("methods = " + JSONObject.toJSONString(methods));

     /*   for (Field field : declaredFields) {
            System.out.println("field.getType().getSimpleName() = " + field.getType().getSimpleName());
            System.out.println("field.getType().getName() = " + field.getType().getName());
            System.out.println("field.getName() = " + field.getName());
        }*/

    }

    @Test
    public void test02() throws Exception {
        String ipayUserId = "2121150000223041";
        String substring = StringUtils.substring(ipayUserId, -3, -1);
        System.out.println("substring = " + substring);

    }

    @Test
    public void test03() {
        Bean bean = new Bean();
        List<Field> fieldList = ReflectionUtil.getSelfAndAllParentAllFild(bean);

        ArrayList<Method> methods = new ArrayList<>();
        for (Field field : fieldList) {
            Method method = ReflectionUtil.getObjectSetMethod(bean, field);
            if (null != method) {
                methods.add(method);
            }
        }
        methods.stream().map(Method::getName).forEach(System.out::println);


    }


}
